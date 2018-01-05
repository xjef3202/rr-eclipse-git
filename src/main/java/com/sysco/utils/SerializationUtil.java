package com.sysco.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SerializationUtil {

    public static String serialize(Object object){
        ObjectMapper mapper = new ObjectMapper();
        String serializedObject=null;
        try {
            serializedObject = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Processing Json Failed ", e);
        }
        return serializedObject;
    }
}
