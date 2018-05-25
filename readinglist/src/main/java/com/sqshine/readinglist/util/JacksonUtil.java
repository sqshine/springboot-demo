/*
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: JacksonUtil
 * Author:   sqshine
 * Date:     2018/5/25 11:31
 * Description: ${DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sqshine.readinglist.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqshine.readinglist.config.JacksonBeanSerializer;
import com.sqshine.readinglist.enums.JacksonSerializerFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * jackson工具类，需要丰富功能 <br>
 *
 * @author sqshine
 * @create 2018/5/25
 * @since 1.0.0
 */
@Slf4j
public class JacksonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    /* 默认时间转换格式 */
    private static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";

    private JacksonUtil() {
    }

    static {
        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(
                new JacksonBeanSerializer(
                        JacksonSerializerFeature.WriteNullStringAsEmpty,
                        JacksonSerializerFeature.WriteNullNumberAsZero,
                        JacksonSerializerFeature.WriteNullListAsEmpty,
                        JacksonSerializerFeature.WriteNullBooleanAsFalse)));
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static ObjectMapper getInstance() {
        return objectMapper;
    }

    public static <T> String toJSONString(T t) throws JsonProcessingException {
        objectMapper.setDateFormat(new SimpleDateFormat(DATEFORMAT));
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(t);
    }

    public static <T> String toJSONStringWithDateFormat(T object, String dateFormat) throws JsonProcessingException {
        if (dateFormat != null && dateFormat.length() != 0) {
            objectMapper.setDateFormat(new SimpleDateFormat(dateFormat));
        }
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }

    @SuppressWarnings("unchecked")
    public static <T> T parseObject(String json, Class<T> clazz) throws IOException {
        return objectMapper.readValue(json, clazz);
    }

    public static Map parseMap(String json) throws IOException {
        return objectMapper.readValue(json, Map.class);
    }

    public static <K, V> Map<K, V> parseMap(String json, Class<? extends Map> mapClass, Class<K> keyClass, Class<V> valueClass) throws IOException {
        JavaType javaType = objectMapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
        return objectMapper.readValue(json, javaType);
    }

    public static <T> Map<String, T> parseMap(String json, Class<T> clazz) throws IOException {
        //代码需要测试是否成功，如果不成功，使用注释掉的方式
        return objectMapper.readValue(json, new TypeReference<Map<String, T>>() {
        });

        /*JavaType javaType = objectMapper.getTypeFactory().constructParametricType(Map.class, String.class, clazz);
        return objectMapper.readValue(json, javaType);*/

        /*Map<String, Map<String, Object>> map = objectMapper.readValue(jsonStr, new TypeReference<Map<String, T>>() {
        });
        Map<String, T> result = new HashMap<>();
        for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
            result.put(entry.getKey(), mapToObject(entry.getValue(), clazz));
        }
        return result;*/
    }


    /**
     * 将字符串转为List
     */
    public static <T> List<T> parseList(String json, Class<T> clazz) throws IOException {
        return objectMapper.readValue(json, new TypeReference<List<T>>() {
        });

        /*JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, clazz);
        return objectMapper.readValue(json, javaType);
        return (List<T>) objectMapper.readValue(json, javaType);*/
    }

    /**
     * 将字符串转为Page对象
     */
    /*public static <T> Page<T> parsePage(String json, Class<T> clazz) throws IOException {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(Page.class, clazz);
        return objectMapper.readValue(json, javaType);
    }*/
}
