package com.yy.yychat.utils;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String objectToJson(Object data) {
        String res = null;
    	try {
			res = MAPPER.writeValueAsString(data);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	return res;
    }

    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        T t = null;
        try {
            t = MAPPER.readValue(jsonData, beanType);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return t;
    }

    public static <T>List<T> jsonToList(String jsonData, Class<T> beanType) {
    	JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        List<T> list = null;
    	try {
    		list = MAPPER.readValue(jsonData, javaType);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return list;
    }
    
}
