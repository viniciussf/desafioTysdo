package com.android.androidannotactions.Util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by vinicius on 7/22/16.
 */
public class DataSerial {
    static ObjectMapper mapper;

    public static void getInstace() {
        if (mapper == null) {
            mapper = new ObjectMapper();
        }
    }

    public static String convertObjectToString(Object object) {
        getInstace();
        try {
            String params = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            return params;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
