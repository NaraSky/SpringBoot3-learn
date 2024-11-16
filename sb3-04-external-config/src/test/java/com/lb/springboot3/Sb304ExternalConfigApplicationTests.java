package com.lb.springboot3;

import com.lb.springboot3.bean.AppBean;
import com.lb.springboot3.service.UserService;
import com.lb.springboot3.service.UserServiceMulti;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Sb304ExternalConfigApplicationTests {

	@Autowired
	private UserService userService;

	@Autowired
	private UserServiceMulti userServiceMulti;

	@Autowired
	private AppBean appBean;

	@Test
	void contextLoads() {
		// userService.printInfo();
		// userServiceMulti.printInfo();
		System.out.println(appBean.toString());


	}

}
