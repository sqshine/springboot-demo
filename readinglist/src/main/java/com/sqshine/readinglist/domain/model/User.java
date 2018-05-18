package com.sqshine.readinglist.domain.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    @JSONField(serialize = false)
    private Long id;
    private String firstname;
    private String lastname;
    private int age;
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    //@JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private Boolean deleted;

    public User() {
    }

    public User(Long id, String firstname, String lastname, int age) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
    }

    public User(Long id, String firstname, String lastname, int age, Date createTime, Boolean deleted) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.createTime = createTime;
        this.deleted = deleted;
    }
}