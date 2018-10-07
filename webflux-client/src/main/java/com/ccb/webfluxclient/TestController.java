package com.ccb.webfluxclient;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class TestController {

    @Autowired
    private IUserApi userApi;


    @GetMapping("/")
    public void test() {

//        userApi.getAllUser();
//        userApi.getUserById("无法访问");
//        userApi.deleteUserById("2e2");
//        userApi.createUser(
//                Mono.just(User.builder().name("test").age(33).build())
//        );



        Flux<User> users = userApi.getAllUser();
        users.subscribe(System.out::println);

    }
}
