package com.sqshine.readinglist.exception;

import com.sqshine.readinglist.enums.ResultEnum;

/**
 * @author sqshine
 */
class BusinessException extends RuntimeException {

    private Integer code;

    public BusinessException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
