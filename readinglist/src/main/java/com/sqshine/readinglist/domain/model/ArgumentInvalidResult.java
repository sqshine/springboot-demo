package com.sqshine.readinglist.domain.model;

import lombok.Data;

/**
 * @author sqshine
 */
@Data
public class ArgumentInvalidResult {
    private String field;
    private Object rejectedValue;
    private String defaultMessage;
}
