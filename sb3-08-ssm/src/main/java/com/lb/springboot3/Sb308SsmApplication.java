package com.lb.springboot3;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.lb.springboot3.repository")
@SpringBootApplication
public class Sb308SsmApplication {

	public static void main(String[] args) {
		SpringApplication.run(Sb308SsmApplication.class, args);
	}

}
