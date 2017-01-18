package com.sqshine.readinglist.web;

import com.alibaba.fastjson.JSON;
import com.sqshine.readinglist.domain.model.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class Home {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Post post) {
        post.setTitle("1");
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String addNewPost(@Valid Post post, BindingResult bindingResult, Model model) {
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
            logger.debug(JSON.toJSONString(bindingResult));
            return "index";
        }
        model.addAttribute("title", post.getTitle());
        model.addAttribute("content", post.getContent());
        return "result";
    }
}