package com.sqshine.readinglist.web;


import com.sqshine.readinglist.domain.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author s
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final Map<Long, User> users = new HashMap<>();

    @PostConstruct
    public void init() {
        users.put(1L, new User(1L, "Jack", "Smith", 20));
        users.put(2L, new User(2L, "Peter", "Johnson", 25));
    }


    @GetMapping("/list/{id}")
    public List<User> getAllList(@PathVariable Integer id) {
        List<User> users = new ArrayList<>();
        users.add(new User(null, "李", null, 20, null, null));
        users.add(new User(2L, "王", "五", 20, new Date(), true));
        if (id == 1) {
            return null;
        }
        return users;
    }

    @GetMapping("/id/1")
    public User getOne() {
        User user = new User(null, "李", null, 20, null, null);
        user.setCreateTime(new Date());
        return user;
    }

    /**
     * 获取所有用户
     *
     * @return flux
     */
    @GetMapping
    public Flux<User> getAll() {
        return Flux.fromIterable(users.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList()));
    }


    /**
     * 获取单个用户
     *
     * @param id id
     * @return mono
     */
    @GetMapping("/{id}")
    public Mono<User> getCustomer(@PathVariable Long id) {
        return Mono.justOrEmpty(users.get(id));
    }

    /**
     * 创建用户
     *
     * @return mono
     */
    @PostMapping
    public Mono<User> postUser(@RequestBody User user) {
        users.put(user.getId(), user);
        return Mono.just(user);
    }

    /**
     * 修改用户
     *
     * @param id   id
     * @param user user
     * @return mono
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<User>> putCustomer(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        users.put(id, user);
        System.out.println("########### PUT:" + user);
        return Mono.just(new ResponseEntity<>(user, HttpStatus.CREATED));
    }

    /**
     * 删除用户
     *
     * @param id id
     * @return mono
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> deleteMethod(@PathVariable Long id) {
        users.remove(id);
        return Mono.just(new ResponseEntity<>("Delete Successfully!", HttpStatus.ACCEPTED));
    }

}
