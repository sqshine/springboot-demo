package com.sqshine.readinglist.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.sqshine.readinglist.interceptors.LogInterceptor;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sqshine
 */
@Configuration
public class WebMvcConfig {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedHeaders("*")
                        .allowedMethods("*")
                        .allowedOrigins("*");
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                // 多个拦截器组成一个拦截器链
                // addPathPatterns 用于添加拦截规则
                // excludePathPatterns 用户排除拦截
                registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**");
            }


        };
    }

    /**
     * FastJson替代Jackson
     * @return HttpMessageConverters
     */
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        // 1、需要先定义一个 convert 转换消息的对象;
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

        //2、添加fastJson 的配置信息，比如：是否要格式化返回的json数据;
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat, SerializerFeature.WriteNullStringAsEmpty);
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");

        //2-1 处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);

        //3、在convert中添加配置信息.
        fastConverter.setFastJsonConfig(fastJsonConfig);

        return new HttpMessageConverters((HttpMessageConverter<?>) fastConverter);
    }

    /*@SuppressWarnings("unchecked")
    @Bean
    public FilterRegistrationBean getXssFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        //在一个filter注册时，如果没指定 DispatcherType ，它将匹配 FORWARD ， INCLUDE 和 REQUEST 。如果启用异步，它也将匹配 ASYNC 。如果迁移 web.xml 中没有 dispatcher 元素的filter，你需要自己指定一个 DispatcherType
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns("/*");
        registration.setName("XssFilter");
        //registration.setOrder(1);
        return registration;
    }*/

}
