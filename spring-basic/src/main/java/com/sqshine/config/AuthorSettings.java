package com.sqshine.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//入口类有@EnableConfigurationProperties(AuthorSettings.class)，则不要加@Component，否则需要加@Component
@Component
//@ConfigurationProperties加载properties文件内的配置，prefix属性指定配置的前缀，locations可以指定自定义的文件位置，不指定默认使用application.properties(application.yml)。
@ConfigurationProperties(prefix = "author",locations = "classpath:author.yml")
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
}
