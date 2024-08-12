package com.redpanda.demo;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple5;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

import org.apache.flink.streaming.connectors.cassandra.CassandraSink;

import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;

import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FlightDataRun {
    private static final Logger LOG = LoggerFactory.getLogger(FlightDataRun.class);
        
    public static void main(String[] args) throws Exception {
        LOG.info("------>>>>>Started: ");
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Assuming the data is in JSON format and coming from the topic "boston.public.bos_air_traffic"
        String topic = "boston.public.bos_air_traffic";
        KafkaSource<FlightEvent> source = KafkaSource.<FlightEvent>builder()
        .setBootstrapServers("redpanda-0:9092")
        .setTopics(topic)
        .setGroupId("flink-group")
        .setStartingOffsets(OffsetsInitializer.earliest())
        .setValueOnlyDeserializer(new FlightEventDeserializationSchema())
        .build();

        DataStream<FlightEvent> stream = env.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Source");

          
        stream.print();

        DataStream<Tuple5<String, Double, Double, Boolean, String>> readyToWriteStream = stream
            .keyBy(FlightEvent::getCallsign)
            .timeWindow(Time.seconds(10)) // Apply a 10 sec tumbling window
            .reduce(new ReduceFunction<FlightEvent>() {
                @Override
                public FlightEvent reduce(FlightEvent flightOne, FlightEvent flightTwo) {
                    // Keep the latest event for the callsign within the window
                    return flightTwo.getTime_position() > flightOne.getTime_position() ? flightTwo : flightOne;
                }
            }, new ProcessWindowFunction<FlightEvent, Tuple5<String, Double, Double, Boolean, String>, String, TimeWindow>() {
                @Override
                public void process(String key, Context context, Iterable<FlightEvent> elements, Collector<Tuple5<String, Double, Double, Boolean, String>> out) {
                    FlightEvent flightEvent = elements.iterator().next(); // Get the deduplicated event
                    out.collect(new Tuple5<>(
                        flightEvent.getCallsign(),
                        flightEvent.getLongitude(),
                        flightEvent.getLatitude(),
                        flightEvent.isOn_ground(),
                        flightEvent.getSquawk()
                    ));
                }
            });

        CassandraSink.addSink(readyToWriteStream)
        .setQuery("INSERT INTO demo.latest_flight_data (callsign, longitude, latitude, on_ground, squawk) VALUES (?, ?, ?, ?, ?);")
        .setHost("scylladb")
        .build();


        
            
        env.execute("Flight Data to Cassandra");
    }

    
}
