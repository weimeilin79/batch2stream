package com.redpanda.demo;

import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.cassandra.CassandraSink;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FlightDataRun {
    private static final Logger LOG = LoggerFactory.getLogger(FlightDataRun.class);
        
    public static void main(String[] args) throws Exception {
        LOG.info("------>>>>>Started: ");
        System.out.println("------>>>>>Started: ");
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

        LOG.info("------>-->>>>stream: "+stream.toString());
        System.out.println("------>-->>>>stream: "+stream.toString());

        
        stream.print();

        DataStream<Tuple5<String, Double, Double, Boolean, String>> readyToWriteStream = stream
        .map(new MapFunction<FlightEvent, Tuple5<String, Double, Double, Boolean, String>>() {
            @Override
            public Tuple5<String, Double, Double, Boolean, String> map(FlightEvent flightEvent) {
                return new Tuple5<>(
                    flightEvent.getCallsign(),
                    flightEvent.getLongitude(),
                    flightEvent.getLatitude(),
                    flightEvent.isOn_ground(),
                    flightEvent.getSquawk()
                );
            }
        });

        CassandraSink.addSink(readyToWriteStream)
        .setQuery("INSERT INTO demo.latest_flight_data (callsign, longitude, latitude, on_ground, squawk) VALUES (?, ?, ?, ?, ?);")
        .setHost("cassandra")
        .build();


        
            
        env.execute("Flight Data to Cassandra");
    }

    
}
