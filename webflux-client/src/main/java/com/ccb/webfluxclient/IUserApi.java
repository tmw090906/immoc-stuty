package com.ccb.webfluxclient;

import com.ccb.ApiServer;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * 解耦 , 解耦 , 解耦????
 * 实现解耦最关键的是, 设计自己的数据结构 + 抽象接口
 */
@ApiServer("http://localhost:8080/user")
public interface IUserApi {

    @GetMapping("/")
    Flux<User> getAllUser();

    @GetMapping("/{id}")
    Mono<User> getUserById(@PathVariable("id") String id);

    @DeleteMapping("/{id}")
    Mono<Void> deleteUserById(@PathVariable("id") String id);

    @PostMapping("/")
    Mono<User> createUser(@RequestBody Mono<User> user);

    @PutMapping("/")
    Mono<User> updateUser(@RequestBody Mono<User> user);

}
