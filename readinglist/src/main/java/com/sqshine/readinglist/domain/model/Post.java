package com.sqshine.readinglist.domain.model;


import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 对于复杂对象，需要@Valid
 * message最好放到常量中处理，不要硬编码
 *
 * @author sqshine
 */
@Data
public class Post {

    @NotBlank(message = "title不能为空")
    @Max(100)
    private String title;

    @Size(min = 3, max = 100, message = "content内容必须在3和100之间")
    private String content;

    @Valid
    private Book book;
}