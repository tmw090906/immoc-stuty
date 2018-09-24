package com.ccb.tmw.chat.service.imp;

import com.ccb.tmw.chat.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase
public class UserServiceImplTest {

    @Autowired
    private IUserService userService;


    @Test
    public void existUsername() {
        System.out.println(userService.existUsername("tianmaowei"));

    }

    @Test
    public void login() {
    }

    @Test
    public void register() {
    }
}