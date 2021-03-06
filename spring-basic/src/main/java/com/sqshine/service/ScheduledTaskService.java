package com.sqshine.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author sqshine
 */
@Service
public class ScheduledTaskService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTaskService.class);
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss n");


    @Scheduled(fixedDelay = 5000)
    public void outputCurrentTime() {
        logger.debug("每隔5秒执行一次:{}", LocalDateTime.now().format(dateTimeFormatter));
    }

    @Scheduled(cron = "0 26 10 ? * *")
    public void fixTimeExecution() {
        logger.info("在指定的时间执行:{}", LocalDateTime.now());
    }

}
