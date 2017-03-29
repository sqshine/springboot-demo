package com.sqshine.readinglist.web;


import com.sqshine.readinglist.domain.model.Greeting;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@Controller
public class GreetingController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @ResponseBody
    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(required = false, defaultValue = "World") String name) {
        System.out.println("==== in greeting ====");
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @ResponseBody
    @PostMapping("/greeting")
    public Greeting greetingPost(@RequestParam(required = false, defaultValue = "World") String name) {
        System.out.println("==== in greeting post====");
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @ResponseBody
    @PutMapping("/greeting")
    public Greeting greetingPut(@RequestParam(required = false, defaultValue = "World") String name) {
        System.out.println("==== in greeting put====");
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @ResponseBody
    @DeleteMapping("/greeting")
    public Greeting greetingDelete(@RequestParam(required = false, defaultValue = "World") String name) {
        System.out.println("==== in greeting delete====");
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
