package com.sqshine.readinglist.exception;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.sqshine.readinglist.domain.model.ArgumentInvalidResult;
import com.sqshine.readinglist.domain.model.Result;
import com.sqshine.readinglist.enums.ResultEnum;
import com.sqshine.readinglist.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sqshine
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理普通异常
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Result defaultErrorHandler(Exception ex) {
        logger.error("error log :", ex);
        //return BusinessErrorRes.create(BusinessExceptionCode.SYSTEM_ERROR, BusinessExceptionCode.getMessage(BusinessExceptionCode.SYSTEM_ERROR));
        return ResultUtil.error(ResultEnum.UNKONW_ERROR.getCode(), ResultEnum.UNKONW_ERROR.getMessage());
    }

    /**
     * 处理自定义业务异常
     */
    @ExceptionHandler(value = {BusinessException.class})
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public Result handleBusinessException(BusinessException ex) {
        logger.info("【业务异常】- {}:{}", ex.getCode(), ex.getMessage());
        return ResultUtil.error(ex.getCode(), ex.getMessage());
    }

    /**
     * 处理参数异常
     */
    @ExceptionHandler(value = {BindException.class, UnrecognizedPropertyException.class, HttpMessageNotReadableException.class, HttpMediaTypeNotSupportedException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Result handleBindException(Exception ex) {
        logger.info("参数Exception:{}", ex);
        return ResultUtil.error(ResultEnum.PARAM_INVALID.getCode(), ResultEnum.PARAM_INVALID.getMessage());
    }

    /**
     * 处理方法参数不合法
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        //按需重新封装需要返回的错误信息
        List<ArgumentInvalidResult> invalidArguments = new ArrayList<>();
        BindingResult bindingResult = ex.getBindingResult();
        //logger.info("方法参数不合法:{}", bindingResult);

        //解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
        for (FieldError error : bindingResult.getFieldErrors()) {
            ArgumentInvalidResult invalidArgument = new ArgumentInvalidResult();
            invalidArgument.setDefaultMessage(error.getDefaultMessage());
            invalidArgument.setField(error.getField());
            invalidArgument.setRejectedValue(error.getRejectedValue());
            invalidArguments.add(invalidArgument);
        }
        return ResultUtil.error(ResultEnum.PARAM_INVALID.getCode(), ResultEnum.PARAM_INVALID.getMessage(), invalidArguments);
    }

    /**
     * 处理上传文件异常
     */
    @ExceptionHandler(value = {MultipartException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Result handleMultipartException(MultipartException ex) {
        logger.info("MultipartException:{}", ex.getMessage());
        return ResultUtil.error(ResultEnum.FILE_SIZE_LARGE.getCode(), ResultEnum.FILE_SIZE_LARGE.getMessage());
    }
}