package com.sqshine.controller;

import com.sqshine.service.DemoAnnotationService;
import com.sqshine.service.DemoMethodService;
import com.sqshine.service.UseHelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ApplicationContext context;
    @Autowired
    private DemoAnnotationService demoAnnotationService;
    @Autowired
    private DemoMethodService demoMethodService;

    @GetMapping("/hello")
    public String sayHello() {
        demoAnnotationService.add();
        demoMethodService.add();

        UseHelloService useHelloService = context.getBean(UseHelloService.class);
        String word = useHelloService.sayHello(" word");
        logger.info("注入内容：{}", word);
        return word;
    }

}
