package com.sqshine;

import com.sqshine.service.HelloService;
import com.sqshine.service.UseHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBasicApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBasicApplication.class, args);

    }

    //java config方式注入bean
    @Bean
    public HelloService helloService() {
        return new HelloService();
    }

    @Bean
    public UseHelloService useHelloService() {
        UseHelloService useHelloService = new UseHelloService();
        useHelloService.setHelloService(helloService());
        return useHelloService;
    }
}
