package com.sqshine.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * @author sqshine
 */
@Service
public class AsyncTaskService {

    private static final Logger logger = LoggerFactory.getLogger(AsyncTaskService.class);

    @Async
    public void addAsyncTask(Integer i) {
        logger.info("执行异步任务：{}", i);
    }

    @Async
    public void addPlusAsyncTask(Integer i) {
        logger.info("执行异步任务+1：{}", i + 1);
    }

    @Async
    public Future<String> sendA() throws Exception {
        logger.info("send A");
        Long startTime = System.currentTimeMillis();
        Thread.sleep(200);
        Long endTime = System.currentTimeMillis();
        logger.info("A耗时：" + (endTime - startTime));
        return new AsyncResult<>("A success");
    }

    @Async
    public Future<String> sendB() throws Exception {
        logger.info("send B");
        Long startTime = System.currentTimeMillis();
        Thread.sleep(300);
        Long endTime = System.currentTimeMillis();
        logger.info("B耗时：" + (endTime - startTime));
        return new AsyncResult<>("B success");
    }
}
