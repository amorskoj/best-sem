package com.perfect.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-08-05.
 */
public class JSONUtils {
    private static ObjectMapper mapper;

    static {
        if (mapper == null)
            mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    }

    public static Map<String, Object> getJsonMapData(final Object o) {
        return new HashMap<String, Object>() {{
            put("rows", getJsonObject(o));
        }};
    }

    /**
     * 获取JSON对象数组
     *
     * @param o
     * @return
     */
    public static ArrayNode getJsonObjectArray(Object o) {
        ArrayNode arrayNode = mapper.createArrayNode();
        try {
            JsonNode jsonNodes = mapper.readTree(mapper.writeValueAsBytes(o));
            for (JsonNode jn : jsonNodes) {
                arrayNode.add(jn);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayNode;
    }

    /**
     * 获取JSON对象
     *
     * @param o
     * @return
     */
    public static JsonNode getJsonObject(Object o) {
        JsonNode jsonObj = null;
        try {
            jsonObj = mapper.readTree(mapper.writeValueAsBytes(o));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    /**
     * 将JSON字符串转换为对应的Java Bean
     *
     * @param jsonStr
     * @param _class
     * @param <T>
     * @return
     */
    public static <T> T getObjectByJson(String jsonStr, Class<T> _class) {
        T t = null;
        try {
            t = mapper.readValue(jsonStr, _class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 将JSON字符串转换成泛型List
     *
     * @param jsonStr
     * @param _class
     * @param <T>
     * @return
     */
    public static <T> List<T> getObjectListByJson(String jsonStr, Class<T> _class) {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, _class);
        List<T> tList = null;
        try {
            tList = mapper.readValue(jsonStr, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tList;
    }

    /**
     * 将JSON字符串转换成泛型Map
     *
     * @param jsonStr
     * @param _class
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> getObjectMapByJson(String jsonStr, Class<T> _class) {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(HashMap.class, String.class, _class);
        Map<String, T> tMap = null;
        try {
            tMap = mapper.readValue(jsonStr, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tMap;
    }

}