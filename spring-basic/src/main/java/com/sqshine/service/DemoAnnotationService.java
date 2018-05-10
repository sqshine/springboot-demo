package com.sqshine.service;

import com.sqshine.annotation.MyAop;
import org.springframework.stereotype.Service;

@Service
public class DemoAnnotationService {

    @MyAop(name = "注解式拦截的add操作")
    public void add() {
    }
}
