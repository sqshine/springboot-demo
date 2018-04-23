package com.sqshine.readinglist.util;

import com.sqshine.readinglist.domain.model.Result;

/**
 * @author sqshine
 */
public class ResultUtil {

    public static <T>Result<T> success(T object) {
        Result<T> result = new Result<>();
        result.setCode(0);
        result.setMessage("成功");
        result.setData(object);
        return result;
    }

    public static Result success() {
        return success(null);
    }

    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(msg);
        return result;
    }

    public static <T>Result<T> error(Integer code, String msg,T data)
    {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(msg);
        result.setData(data);
        return result;
    }
}
