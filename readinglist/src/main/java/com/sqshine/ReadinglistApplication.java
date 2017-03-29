package com.sqshine;

import com.sqshine.readinglist.enums.Events;
import com.sqshine.readinglist.enums.States;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@MapperScan("com.sqshine.readinglist.domain.mapper")
public class ReadinglistApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ReadinglistApplication.class);

    //@Autowired
    //private StringRedisTemplate template;

    @Autowired
    private StateMachine<States, Events> stateMachine;

    public static void main(String[] args) {
        SpringApplication.run(ReadinglistApplication.class, args);
    }

    //使用fastjson作为json解析器
/*    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastConverter;
        return new HttpMessageConverters(converter);
    }*/

    @Override
    public void run(String... args) throws Exception {
/*        ValueOperations<String, String> ops = template.opsForValue();
        String key = "spring.boot.redis.test";
        if (template.hasKey(key)) {
            ops.getOperations().delete(key);
            logger.debug("删除 redis key [{}] 数据", key);
        }
        ops.set(key, "redis的值");
        //ops.setIfAbsent(key, "redis的值");
        logger.debug("设置 redis key [{}] , value = {} ", key, ops.get(key));*/

        stateMachine.start();
        stateMachine.sendEvent(Events.PAY);
        stateMachine.sendEvent(Events.RECEIVE);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD");
            }
        };
    }
}
