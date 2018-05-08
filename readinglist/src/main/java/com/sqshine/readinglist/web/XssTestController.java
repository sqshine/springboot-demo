package com.sqshine.readinglist.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/xss")
public class XssTestController {

    @RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getName(String name) {
        return name;
    }

}
