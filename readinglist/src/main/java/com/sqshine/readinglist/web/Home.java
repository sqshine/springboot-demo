package com.sqshine.readinglist.web;

import com.alibaba.fastjson.JSON;
import com.sqshine.readinglist.domain.model.Book;
import com.sqshine.readinglist.domain.model.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author sqshine
 * <p>
 * 此处thymeleaf模板代码有问题，不要使用
 */
@Controller
public class Home {
    private static final Logger logger = LoggerFactory.getLogger(Home.class);

    @GetMapping("/")
    public String index(Post post) {
        post.setTitle("Index-首页");
        return "index";
    }

    /**
     * 测试参数不通过时，转到全局异常处理的参数异常处理
     *
     * @param book 实体
     * @return book 对象
     */
    @PostMapping("/book")
    @ResponseBody
    public Book validPost(@Validated @RequestBody Book book) {
        return book;
    }

    @PostMapping("/")
    public String addNewPost(@Validated Post post, BindingResult bindingResult, Model model) {
        Book book = new Book();
        book.setTitle("book title");
        post.setBook(book);
        if (bindingResult.hasErrors()) {

/*            for (ObjectError oe : bindingResult.getAllErrors()) {
                if (!(oe instanceof FieldError)) {
                    updatedBindingResult.addError(oe);
                } else {
                    FieldError fieldError = (FieldError) oe;

                    String rejectedValue = null;   // that's the point, create a copy of the FieldError, emptying the rejectedValue;

                    FieldError updatedFieldError = new FieldError(
                            MY_FORM_OBJECT_NAME,
                            fieldError.getField(),
                            rejectedValue,
                            fieldError.isBindingFailure(),
                            fieldError.getCodes(),
                            fieldError.getArguments(),
                            fieldError.getDefaultMessage());
                    updatedBindingResult.addError(updatedFieldError);
                }
            }*/
            /*  List<ObjectError> list = bindingResult.getAllErrors();
            for (ObjectError error : list) {
                logger.debug("ObjectError code:{},argments:{},message:{}", error.getCode(), error.getArguments(), error.getDefaultMessage());
                logger.debug("ObjectError json:{}", JSON.toJSONString(error));
            }*/
            for (FieldError error : bindingResult.getFieldErrors()) {
                logger.debug("FieldError Field:{},message:{}", error.getField(), error.getDefaultMessage());
                logger.debug("FieldError json:{}", JSON.toJSONString(error));
                //注意如果book可为null，则去掉book的校验信息
            }
            //logger.debug(JSON.toJSONString(bindingResult));
            return "index";
        }
        model.addAttribute("title", post.getTitle());
        model.addAttribute("content", post.getContent());
        return "result";
    }

    @PutMapping("/")
    @ResponseBody
    public String addNew(@Validated @RequestBody Post post, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                logger.debug("FieldError Field:{},message:{}", error.getField(), error.getDefaultMessage());
                logger.debug("FieldError json:{}", JSON.toJSONString(error));
            }
            return JSON.toJSONString(bindingResult);
        }
        return "success";
    }
}