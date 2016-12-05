package com.sqshine.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ScheduledTaskService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Scheduled(fixedDelay = 5000)
    public void outputCurrentTime() {
        logger.info("每隔5秒执行一次:{}", LocalDateTime.now());
    }

    @Scheduled(cron = "0 18 10 ? * *")
    public void fixTimeExecution() {
        logger.info("在指定的时间执行:{}", LocalDateTime.now());
    }

}
