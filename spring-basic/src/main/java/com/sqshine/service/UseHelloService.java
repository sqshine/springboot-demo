package com.sqshine.service;

public class UseHelloService {
    private HelloService helloService;

    public void setHelloService(HelloService helloService) {
        this.helloService = helloService;
    }

    public String sayHello(final String word) {
        return helloService.sayHello(word);
    }
}
