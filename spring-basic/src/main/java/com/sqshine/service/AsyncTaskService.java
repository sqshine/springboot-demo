package com.sqshine.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncTaskService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Async
    public void addAsyncTask(Integer i) {
        logger.info("执行异步任务：{}", i);
    }

    @Async
    public void addPlusAsyncTask(Integer i) {
        logger.info("执行异步任务+1：{}", i + 1);
    }
}
