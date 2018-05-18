package com.sqshine.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AsyncTaskServiceTest {

    @Autowired
    private AsyncTaskService asyncTaskService;

    @Test
    public void futureTaskTest() throws Exception {
        long startTime = System.currentTimeMillis();

        Future<String> task1 = asyncTaskService.sendA();
        Future<String> task2 = asyncTaskService.sendB();

        while (true) {
            if (task1.isDone() && task2.isDone()) {
                break;
            }
        }

        log.info("A返回：{}", task1.get());
        log.info("B返回：{}", task2.get());


        long endTime = System.currentTimeMillis();
        log.info("总耗时：" + (endTime - startTime));
    }

    @Test
    public void async() {
        for (int i = 0; i < 1000; i++) {
            asyncTaskService.addAsyncTask(i);
            asyncTaskService.addPlusAsyncTask(i);
        }
        log.info("task finish");
    }
}