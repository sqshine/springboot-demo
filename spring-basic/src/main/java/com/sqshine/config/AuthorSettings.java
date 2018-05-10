package com.sqshine.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import com.sqshine.util.SpringContextHolder;

/**
 * @author sqshine
 * <p>
 * 入口类有@EnableConfigurationProperties(AuthorSettings.class)，则不要加@Component，否则需要加@Component
 * 配置 @ConfigurationProperties加载properties文件内的配置，prefix属性指定配置的前缀，不指定默认使用application.properties(application.yml)。
 * 配置 @PropertySource 不支持YAML文件，只能使用properties文件.
 * ≈
 */
@Component
@ConfigurationProperties(prefix = "author")
@PropertySource(value = "classpath:author.properties")
public class AuthorSettings {
    private String name;
    private String gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public static AuthorSettings me() {
        return SpringContextHolder.getBean(AuthorSettings.class);
    }
}
