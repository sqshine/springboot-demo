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

    /**
     * 400 - Bad Request
     */
    /*@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public AjaxResult handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        logger.error("缺少请求参数", e);
        return new AjaxResult().failure("required_parameter_is_not_present");
    }*/

    /**
     * 400 - Bad Request
     */
    /*@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public AjaxResult handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.error("参数解析失败", e);
        return new AjaxResult().failure("could_not_read_json");
    }*/

    /**
     * 400 - Bad Request
     */
    /*@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public AjaxResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error("参数验证失败", e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);
        return new AjaxResult().failure(message);
    }*/

    /**
     * 400 - Bad Request
     */
    /*@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public AjaxResult handleBindException(BindException e) {
        logger.error("参数绑定失败", e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);
        return new AjaxResult().failure(message);
    }
*/
    /**
     * 400 - Bad Request
     */
    /*@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public AjaxResult handleServiceException(ConstraintViolationException e) {
        logger.error("参数验证失败", e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String message = violation.getMessage();
        return new AjaxResult().failure("parameter:" + message);
    }*/

    /**
     * 400 - Bad Request
     */
   /* @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public AjaxResult handleValidationException(ValidationException e) {
        logger.error("参数验证失败", e);
        return new AjaxResult().failure("validation_exception");
    }*/

    /**
     * 405 - Method Not Allowed
     */
   /* @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public AjaxResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.error("不支持当前请求方法", e);
        return new AjaxResult().failure("request_method_not_supported");
    }*/

    /**
     * 415 - Unsupported Media Type
     */
    /*@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public AjaxResult handleHttpMediaTypeNotSupportedException(Exception e) {
        logger.error("不支持当前媒体类型", e);
        return new AjaxResult().failure("content_type_not_supported");
    }*/


    /**
     * 操作数据库出现异常:名称重复，外键关联
     */
    /*@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public AjaxResult handleException(DataIntegrityViolationException e) {
        logger.error("操作数据库出现异常:", e);
        return new AjaxResult().failure("操作数据库出现异常：字段重复、有外键关联等");
    }*/
}