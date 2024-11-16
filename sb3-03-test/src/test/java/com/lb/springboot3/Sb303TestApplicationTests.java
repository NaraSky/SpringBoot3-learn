package com.lb.springboot3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Sb303TestApplicationTests {


	@Autowired
	private UserService userService;


	@Test
	void contextLoads() {
		userService.save();
	}

}
