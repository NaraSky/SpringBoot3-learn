package com.lb.springboot3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class Sb302CoreAnnoApplication {

	@Bean
	public Date getNowDate(){ // 方法名作为bean的id
		return new Date();
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(Sb302CoreAnnoApplication.class, args);
		Date bean = configurableApplicationContext.getBean(Date.class);
		System.out.println(bean);

		Date getNewDate = (Date) configurableApplicationContext.getBean("getNowDate");
		System.out.println(getNewDate);
	}

}
