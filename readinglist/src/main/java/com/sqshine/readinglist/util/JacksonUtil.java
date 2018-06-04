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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sqshine.readinglist.config.JacksonBeanSerializer;
import com.sqshine.readinglist.enums.JacksonSerializerFeature;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * jackson工具类 <br />
 *
 * @author sqshine
 * @create 2018/5/25
 * @since 1.0.0
 */
public class JacksonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();
    /**
     * 默认时间转换格式
     */
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
        //不输出null的对象
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //pretty json
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static ObjectMapper getInstance() {
        return objectMapper;
    }

    /**
     * 对象转json，默认格式化日期为"yyyy-MM-dd HH:mm:ss"
     */
    public static <T> String toJSONString(T t) throws JsonProcessingException {
        objectMapper.setDateFormat(new SimpleDateFormat(DATEFORMAT));
        return objectMapper.writeValueAsString(t);
    }

    /**
     * 对象转json，格式化日期
     */
    public static <T> String toJSONStringWithDateFormat(T object, String dateFormat) throws JsonProcessingException {
        if (dateFormat != null && dateFormat.length() != 0) {
            objectMapper.setDateFormat(new SimpleDateFormat(dateFormat));
        }
        return objectMapper.writeValueAsString(object);
    }

    /**
     * json转对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T parseObject(String jsonString, Class<T> clazz) throws IOException {
        return objectMapper.readValue(jsonString, clazz);
    }

    /**
     * json转map
     */
    public static <K, V> Map<K, V> parseMap(String jsonString, Class<? extends Map> mapClass, Class<K> keyClass, Class<V> valueClass) throws IOException {
        JavaType javaType = objectMapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
        return objectMapper.readValue(jsonString, javaType);
    }

    /**
     * json转map
     */
    public static <K, V> Map<K, V> parseMap(String jsonString, Class<K> keyClass, Class<V> valueClass) throws IOException {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(Map.class, keyClass, valueClass);
        return objectMapper.readValue(jsonString, javaType);
    }

    /**
     * json转List
     */
    public static <T> List<T> parseList(String jsonString, Class<T> clazz) throws IOException {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, clazz);
        return objectMapper.readValue(jsonString, javaType);
    }
}
