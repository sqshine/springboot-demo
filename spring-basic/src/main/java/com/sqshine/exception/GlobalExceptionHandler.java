package com.sqshine.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author sqshine
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    /**
     * 处理自定义业务异常
     */
    @ExceptionHandler(value = {MyException.class})
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public String handleBusinessException(MyException ex) {
        logger.info("【业务异常】- {}:{}", ex.getMessage());
        return ex.getMessage();
    }

}