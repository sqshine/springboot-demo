package com.sqshine.readinglist.util;

import com.sqshine.readinglist.domain.model.Result;
import com.sqshine.readinglist.enums.ResultEnum;

/**
 * @author sqshine
 */
public class ResultUtil {

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMessage(ResultEnum.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    public static Result success() {
        return success(null);
    }


    public static <T> Result<T> error(Integer code, String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static Result error(Integer code, String message) {
        return error(code, message, null);
    }
}
