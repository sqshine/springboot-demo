package com.sqshine.readinglist.domain.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sqshine
 * <p>
 * http请求返回的最外层对象
 */
@Getter
@Setter
public class Result<T> {

    /**
     * 返回码
     */
    private Integer code;
    /**
     * 提示信息
     */
    private String message;
    /**
     * 返回数据
     */
    //@JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public Integer getCode() {
        return code;
    }
}
