package com.sqshine.readinglist.web;

import com.sqshine.readinglist.enums.ResultEnum;
import com.sqshine.readinglist.exception.BusinessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionController {

    @GetMapping("/ex/{id}")
    public int zeroException(@PathVariable Integer id) {
        if (id == 1) {
            throw new BusinessException(ResultEnum.TOKEN_URL_OVERDUE);
        } else {
            return 100;
        }
    }
}
