package com.sqshine.readinglist.enums;

/**
 * @author sqshine
 */

public enum ResultEnum {

    UNKONW_ERROR(-1, "未知错误"),
    SUCCESS(0, "成功"),
    FILE_SIZE_LARGE(900, "文件的总大小不能超过10MB，请压缩后重新上传"),
    PERMISSION_DENY(901, "越权操作"),
    OPERATE_FAIL(902, "操作失败"),
    PARAM_INVALID(903, "参数不正确"),
    TOKEN_URL_OVERDUE(904, "链接已失效"),
    GET_MUTEX_LOCK_FAIL(905, "当前业务正在进行，请勿重复发起"),

    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}