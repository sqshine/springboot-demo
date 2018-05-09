package com.sqshine.readinglist.exception;

import com.sqshine.readinglist.enums.ResultEnum;

/**
 * @author sqshine
 */
public class BusinessException extends RuntimeException {

    private Integer code;

    private ResultEnum resultEnum;

    public BusinessException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
        this.resultEnum = resultEnum;
    }

    public Integer getCode() {
        return code;
    }

    public ResultEnum getResultEnum() {
        return resultEnum;
    }
}
