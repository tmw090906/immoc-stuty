package com.ccb.tmw.chat;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ccb.tmw.chat", "org.n3r.idworker"})
@MapperScan(basePackages = "com.ccb.tmw.chat.mapper")
public class NettyChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(NettyChatApplication.class, args);
	}
}
