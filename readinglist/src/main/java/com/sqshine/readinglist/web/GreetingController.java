package com.sqshine.readinglist.web;


import com.sqshine.readinglist.domain.model.Greeting;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author sqshine
 */
@RestController
public class GreetingController {
    private static final String TEMPLATE = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(required = false, defaultValue = "World") String name) {
        System.out.println("==== in greeting ====");
        return new Greeting(counter.incrementAndGet(), String.format(TEMPLATE, name));
    }

    @PostMapping("/greeting")
    public Greeting greetingPost(@RequestParam(required = false, defaultValue = "World-post") String name) {
        System.out.println("==== in greeting post====");
        return new Greeting(counter.incrementAndGet(), String.format(TEMPLATE, name));
    }

    @PutMapping("/greeting")
    public Greeting greetingPut(@RequestParam(required = false, defaultValue = "World-put") String name) {
        System.out.println("==== in greeting put====");
        return new Greeting(counter.incrementAndGet(), String.format(TEMPLATE, name));
    }

    @DeleteMapping("/greeting")
    public Greeting greetingDelete(@RequestParam(required = false, defaultValue = "World-delete") String name) {
        System.out.println("==== in greeting delete====");
        return new Greeting(counter.incrementAndGet(), String.format(TEMPLATE, name));
    }
}
