package com.sqshine.readinglist.domain.model;

import lombok.Data;

@Data
public class Country {

    private Integer id;

    /**
     * 名称
     */
    private String countryname;

    /**
     * 代码
     */
    private String countrycode;
}