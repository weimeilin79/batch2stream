package com.redpanda.demo;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;

public class FlightEventDeserializationSchema implements DeserializationSchema<FlightEvent> {

    private static final long serialVersionUID = 1L;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public FlightEvent deserialize(byte[] message) throws IOException {

        if (message == null) {
            //JUST BYPASS THE ERROR
            return null;
        }

        JsonNode rootNode = objectMapper.readTree(message);
        

        if(rootNode.isMissingNode()){
            //JUST BYPASS THE ERROR
            return null;
        }
        JsonNode payloadNode =  rootNode.path("payload");
        

        FlightEvent result = null;

        if (!payloadNode.isMissingNode()) { // Check if the payload node exists
            try{
                result =  objectMapper.treeToValue(payloadNode, FlightEvent.class);
                return result;
            }catch (Exception e){
                //JUST BYPASS THE ERROR
                return result;
            }
                
        }else {
            throw new IOException("Unable to deserialize the message, PAYLOAD is NULL");
        }
        
    }

    @Override
    public boolean isEndOfStream(FlightEvent nextElement) {
        return false;
    }

    @Override
    public TypeInformation<FlightEvent> getProducedType() {
        return TypeInformation.of(FlightEvent.class);
    }
}
