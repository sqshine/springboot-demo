package com.sqshine.readinglist.domain.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

/**
 * @author sqshine
 */
@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "reader不能为空")
    private String reader;
    @NotBlank(message = "ISBN不能为空")
    private String isbn;
    private String title;
    private String author;
    private String description;
}
