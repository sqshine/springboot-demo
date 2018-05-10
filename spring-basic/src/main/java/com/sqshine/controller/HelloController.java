package com.sqshine.controller;

import com.sqshine.annotation.Permission;
import com.sqshine.config.AuthorSettings;
import com.sqshine.service.AsyncTaskService;
import com.sqshine.service.DemoAnnotationService;
import com.sqshine.service.DemoMethodService;
import com.sqshine.service.UseHelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sqshine
 */
@RestController
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private ApplicationContext context;
    @Autowired
    private DemoAnnotationService demoAnnotationService;
    @Autowired
    private DemoMethodService demoMethodService;
    @Autowired
    private AsyncTaskService asyncTaskService;
/*    @Autowired
    private AuthorSettings authorSettings;*/

    @Value("${book.author}")
    private String bookAuthor;
    @Value("${book.name}")
    private String bookName;


    @GetMapping("/p1")
    @Permission("permission")
    public String permissonAllowed() {
        return "permission allowed";
    }

    @GetMapping("/p2")
    @Permission
    public String permissonNotAllowed() {
        return "permission 无法返回";
    }

    @GetMapping("/hello")
    public String sayHello() {

        //AOP方法
        demoAnnotationService.add();
        demoMethodService.add();

        UseHelloService useHelloService = context.getBean(UseHelloService.class);
        String word = useHelloService.sayHello(" word");
        logger.info("注入内容：{}", word);
        logger.info("书名：{},作者：{}", bookName, bookAuthor);

        // logger.info("作者：{},性别：{}", authorSettings.getName(), authorSettings.getGender());
        logger.info("作者：{},性别：{}", AuthorSettings.me().getName(), AuthorSettings.me().getGender());
        return word;
    }

    @GetMapping("/async")
    public String async() {
        for (int i = 0; i < 1000; i++) {
            asyncTaskService.addAsyncTask(i);
            asyncTaskService.addPlusAsyncTask(i);
        }
        return "OK";
    }

}
