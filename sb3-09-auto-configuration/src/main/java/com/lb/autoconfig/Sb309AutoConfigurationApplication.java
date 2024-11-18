package com.lb.autoconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Sb309AutoConfigurationApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(Sb309AutoConfigurationApplication.class, args);
		String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
		for (String beanDefinitionName : beanDefinitionNames) {
			// System.out.println(beanDefinitionName);
			if (beanDefinitionName.equals("a")) {
				System.out.println("beanDefinitionName = " + beanDefinitionName);
			} else if (beanDefinitionName.equals("b")) {
				System.out.println("beanDefinitionName = " + beanDefinitionName);
			} else if (beanDefinitionName.equals("getA")) {
				System.out.println("beanDefinitionName = " + beanDefinitionName);
			}

		}
		// System.out.println("applicationContext.getBeanDefinitionCount() = " + applicationContext.getBeanDefinitionCount());
	}

}
