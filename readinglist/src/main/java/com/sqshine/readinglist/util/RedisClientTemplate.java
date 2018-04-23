package com.sqshine.readinglist.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sqshine
 */
@Component
public final class RedisClientTemplate {

    private static final Logger log = LoggerFactory.getLogger(RedisClientTemplate.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void setObject(String key, Object obj) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String jsonObject = JSONObject.toJSONString(obj);
        ops.set(key, jsonObject);
    }

    /**
     * 获取redis里的json对象，转换为对象
     *
     * @param key   键
     * @param clazz 对象类型
     * @return Object
     */
    public <T> T getObject(String key, Class<T> clazz) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String jsonObject = ops.get(key);
        if (StringUtils.isEmpty(jsonObject)) {
            return null;
        }
        return JSON.parseObject(jsonObject, clazz);
    }

    /**
     * 获取list对象
     *
     * @param key   键
     * @param clazz 对象类型
     * @return List<T>
     */
    public <T> List<T> getList(String key, Class<T> clazz) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String jsonObject = ops.get(key);
        if (StringUtils.isEmpty(jsonObject)) {
            return new ArrayList<>();
        }
        return JSON.parseArray(jsonObject, clazz);
    }

}