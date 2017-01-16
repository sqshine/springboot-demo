package com.sqshine;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.converter.HttpMessageConverter;

@SpringBootApplication
@MapperScan("com.sqshine.readinglist.domain.mapper")
public class ReadinglistApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ReadinglistApplication.class);

    @Autowired
    private StringRedisTemplate template;

    public static void main(String[] args) {
        SpringApplication.run(ReadinglistApplication.class, args);
    }

    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastConverter;
        return new HttpMessageConverters(converter);
    }

    @Override
    public void run(String... args) throws Exception {
        ValueOperations<String, String> ops = template.opsForValue();
        String key = "spring.boot.redis.test";
        if (template.hasKey(key)) {
            ops.getOperations().delete(key);
            logger.debug("删除 redis key [{}] 数据", key);
        }
        ops.set(key, "redis的值");
        //ops.setIfAbsent(key, "redis的值");
        logger.debug("设置 redis key [{}] , value = {} ", key, ops.get(key));
    }
}
