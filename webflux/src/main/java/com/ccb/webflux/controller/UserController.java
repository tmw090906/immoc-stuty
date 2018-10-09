package com.ccb.webflux.controller;


import com.ccb.webflux.domain.User;
import com.ccb.webflux.repository.UserRepository;
import com.ccb.webflux.util.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * 传统模式获取所有用户列表
     * @return
     */
    @GetMapping("/")
    public Flux<User> getAll() {
        return userRepository.findAll();
    }


    /**
     * 以SSE形式多次返回数据
     * @return
     */
    @GetMapping(value = "/stream/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamGetAll() {
        return userRepository.findAll();
    }


    /**
     * 新增数据
     * @param user
     * @return
     */
    @PostMapping("/")
    public Mono<User> craeteUser(@Valid @RequestBody User user) {
        // spring data jap
        // 新增和修改都是save ，有id是修改， id为空是新增
        // 新增一条数据把id置空即可
        user.setId(null);
        CheckUtils.checkName(user.getName());
        return this.userRepository.save(user);
    }


    /**
     * 根据id删除用户
     * 存在的时候返回200， 不存在返回404
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(
            @PathVariable("id") String id) {
        // deleteById 没有返回值， 不能判断数据是否存在
        // this.userRepository.deleteById(id);
        return this.userRepository.findById(id)
                // flatMap : 对Mono中(Stream流)的<T>数据进行操作, 并返回一个Mono<T>
                // map : 对Mono中的<T>进行操作, 并改变Mono<T> 其中的数据流
                .flatMap(user -> this.userRepository.deleteById(id).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 修改数据
     * 存在的时候返回200和修改后的数据, 不存在的时候返回404
     * @param id
     * @param user
     * @return
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<User>> updateUser(
            @PathVariable("id") String id,
            @Valid @RequestBody User user) {
        
        return this.userRepository.findById(id)
                // 先使用flatMap 对
                // userRepository.findById(id)返回的Mono<User> 中的User进行操作
                // 并返回一个Mono<User>
                .flatMap(u -> {
                    u.setAge(user.getAge());
                    u.setName(user.getName());
                    return this.userRepository.save(u);
                })
                // 此时Mono<User>不满足返回值
                // 使用map 将Mono<User> 中的User -> 转化为 ResponseEntity<User>
                // 此时转化为Mono<ResponseEntity<User>>
                .map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 通过Id查找数据
     * 存在时返回数据+200 不存在时返回404
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> findUserById (@PathVariable("id") String id) {
        return this.userRepository.findById(id)
                // 只需要对Mono中的User转化为ResponseEntity<User>
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 通过年龄范围查找数据
     * @param start
     * @param end
     * @return
     */
    @GetMapping("/age/{start}/{end}")
    public Flux<User> findByAge (
            @PathVariable("start") int start,
            @PathVariable("end") int end) {
        return this.userRepository.findByAgeBetween(start, end);
    }

    /**
     * 通过年龄范围查找数据
     * @param start
     * @param end
     * @return
     */
    @GetMapping(value = "/stream/age/{start}/{end}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamFindByAge (
            @PathVariable("start") int start,
            @PathVariable("end") int end) {
        return this.userRepository.findByAgeBetween(start, end);
    }


    /**
     * 查找20 - 30岁的用户
     * @return
     */
    @GetMapping("/age/old")
    public Flux<User> findByAge () {
        return this.userRepository.oldUser();
    }

    /**
     * 查找20 - 30岁的用户
     * @return
     */
    @GetMapping(value = "/stream/age/old", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamfindByAge () {
        return this.userRepository.oldUser();
    }





}
