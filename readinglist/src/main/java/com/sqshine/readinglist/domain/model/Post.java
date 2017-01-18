package com.sqshine.readinglist.domain.model;

import javax.validation.constraints.Size;

public class Post {

    @Size(min = 4, max = 35)
    private String title;

    @Size(min = 3, max = 1000)
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}