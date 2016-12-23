package com.sqshine;

import com.sqshine.conditional.LinuxCondition;
import com.sqshine.conditional.WindowsCondition;
import com.sqshine.service.HelloService;
import com.sqshine.service.UseHelloService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableAsync
@EnableScheduling
//@EnableConfigurationProperties 加上这个不用AuthorSettings类上加@Component，否则需要加上@Component
//@EnableConfigurationProperties(AuthorSettings.class)
public class SpringBasicApplication {

    public static void main(String[] args) {
        //new SpringApplicationBuilder(SpringBasicApplication.class).bannerMode(Banner.Mode.OFF).run(args);
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

    @Bean
    @Conditional(WindowsCondition.class)
    public String windowsCondition() {
        System.out.println("条件加载：windows");
        return "windows";
    }

    @Bean
    @Conditional(LinuxCondition.class)
    public String linuxCondition() {
        System.out.println("条件加载：linux");
        return "linux";
    }
}