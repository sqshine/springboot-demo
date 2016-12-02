package com.sqshine.Controller;

import com.sqshine.service.UseHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private ApplicationContext context;

    @GetMapping("/hello")
    public String sayHello() {
        UseHelloService useHelloService = context.getBean(UseHelloService.class);
        String word = useHelloService.sayHello(" word");
        System.out.println("注入内容：" + word);
        return word;
    }

}
