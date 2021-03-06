package com.sqshine.readinglist.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sqshine.readinglist.domain.model.Result;
import com.sqshine.readinglist.enums.JacksonSerializerFeature;
import com.sqshine.readinglist.enums.ResultEnum;
import com.sqshine.readinglist.filter.XssFilter;
import com.sqshine.readinglist.interceptors.LogInterceptor;
import com.sqshine.readinglist.util.JacksonUtil;
import com.sqshine.readinglist.util.ResultUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author sqshine
 */
@Configuration
public class WebMvcConfig {

    private static final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

    private static final String PROD = "prod";
    private static final String UNKNOWN = "unknown";
    private static final String COMMA = ",";

    /**
     * 当前激活的配置文件
     */
    @Value("${spring.profiles.active}")
    private String env;

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
                //接口签名认证拦截器，该签名认证比较简单，实际项目中可以使用Json Web Token或其他更好的方式替代。
                //开发环境忽略签名认证
                if (PROD.equals(env)) {
                    registry.addInterceptor(new HandlerInterceptorAdapter() {
                        @Override
                        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws JsonProcessingException {
                            //验证签名
                            boolean pass = validateSign(request);
                            if (pass) {
                                return true;
                            } else {
                                Map<String, String[]> parameterMap = request.getParameterMap();
                                logger.warn("签名认证失败，请求接口：{}，请求IP：{}，请求参数：{}", request.getRequestURI(), getIpAddress(request), JacksonUtil.toJSONString(parameterMap));
                                Result result = ResultUtil.error(ResultEnum.UNAUTHORIZED);
                                responseResult(response, result);
                                return false;
                            }
                        }
                    });
                }
                registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**");
            }
        };
    }

    /**
     * FastJson替代Jackson
     *
     * @return HttpMessageConverters
     */
    @Bean
    @Profile("prod")
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        // 1、需要先定义一个 convert 转换消息的对象;
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

        //2、添加fastJson 的配置信息，比如：是否要格式化返回的json数据;
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullBooleanAsFalse);
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");

        //2-1 处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);

        //3、在convert中添加配置信息.
        fastConverter.setFastJsonConfig(fastJsonConfig);

        return new HttpMessageConverters((HttpMessageConverter<?>) fastConverter);
    }

    /**
     * Jackson定制返回json
     *
     * @return HttpMessageConverters
     */
    @Bean
    @Profile({"dev", "test"})
    public HttpMessageConverters jacksonHttpMessageConverters() {
        // 1、需要先定义一个 convert 转换消息的对象;
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        //2、添加jackson的配置信息，比如：是否要格式化返回的json数据;
        ObjectMapper objectMapper = converter.getObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                //不输出null的对象
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                //pretty json
                .enable(SerializationFeature.INDENT_OUTPUT)
                .setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(
                        new JacksonBeanSerializer(JacksonSerializerFeature.WriteNullStringAsEmpty,
                                JacksonSerializerFeature.WriteNullNumberAsZero,
                                JacksonSerializerFeature.WriteNullListAsEmpty,
                                JacksonSerializerFeature.WriteNullBooleanAsFalse)))
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        //3、在convert中添加配置信息.
        return new HttpMessageConverters((HttpMessageConverter<?>) converter);
    }

    /**
     * xssFilter注册
     */
    @SuppressWarnings("unchecked")
    @Bean
    public FilterRegistrationBean xssFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean(new XssFilter());
        //在一个filter注册时，如果没指定 DispatcherType ，它将匹配 FORWARD ， INCLUDE 和 REQUEST 。如果启用异步，它也将匹配 ASYNC 。如果迁移 web.xml 中没有 dispatcher 元素的filter，你需要自己指定一个 DispatcherType
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.addUrlPatterns("/*");
        //excludedUrls可以写到配置文件中,由XssFilter获取并解析，如果需要，可以增加带*的解析，如/druid/*
        registration.addInitParameter("excludedUrls", "/favicon.ico,.js,.gif,.jpg,.png,.css,.ico,/country,/druid");
        registration.setName("XssFilter");
        registration.setOrder(1);
        return registration;
    }

    private void responseResult(HttpServletResponse response, Result result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JacksonUtil.toJSONString(result));
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }

    /**
     * 一个简单的签名认证，规则：
     * 1. 将请求参数按ascii码排序
     * 2. 拼接为a=value&b=value...这样的字符串（不包含sign）
     * 3. 混合密钥（secret）进行md5获得签名，与请求的签名进行比较
     */
    private boolean validateSign(HttpServletRequest request) {
        //获得请求签名，如sign=19e907700db7ad91318424a97c54ed57
        String requestSign = request.getParameter("sign");
        if (StringUtils.isEmpty(requestSign)) {
            return false;
        }
        List<String> keys = new ArrayList<>(request.getParameterMap().keySet());
        //排除sign参数
        keys.remove("sign");
        //排序
        Collections.sort(keys);

        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            //拼接字符串
            sb.append(key).append("=").append(request.getParameter(key)).append("&");
        }
        String linkString = sb.toString();
        //去除最后一个'&'
        linkString = StringUtils.substring(linkString, 0, linkString.length() - 1);
        //密钥，自己修改
        String secret = "Potato";
        //混合密钥md5
        String sign = DigestUtils.md5Hex(linkString + secret);
        //比较
        return StringUtils.equals(sign, requestSign);
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 参考文章： http://developer.51cto.com/art/201111/305181.htm
     * <p>
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     * <p>
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     * <p>
     * 用户真实IP为： 192.168.1.110
     *
     * @param request httpRequest
     * @return ip
     */
    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 如果是多级代理，那么取第一个ip为客户端ip
        if (ip != null && ip.contains(COMMA)) {
            ip = ip.substring(0, ip.indexOf(COMMA)).trim();
        }

        return ip;
    }
}
