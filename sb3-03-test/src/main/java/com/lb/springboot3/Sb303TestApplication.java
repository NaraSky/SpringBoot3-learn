package com.lb.springboot3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Sb303TestApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(Sb303TestApplication.class, args);
		UserService userService = (UserService) configurableApplicationContext.getBean("userServiceImpl");
		userService.save();
	}

}
