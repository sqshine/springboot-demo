package com.sqshine.readinglist.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionController {

    @GetMapping("/e")
    public int zeroException() {
        return 100 / 0;
    }
}
