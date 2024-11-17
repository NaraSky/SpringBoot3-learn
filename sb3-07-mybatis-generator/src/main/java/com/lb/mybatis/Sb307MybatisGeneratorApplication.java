package com.lb.mybatis;

import com.lb.mybatis.model.Vip;
import com.lb.mybatis.repository.VipDao;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@MapperScan(basePackages = "com.lb.mybatis.repository")
@SpringBootApplication
public class Sb307MybatisGeneratorApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(Sb307MybatisGeneratorApplication.class, args);
		VipDao vipMapper = applicationContext.getBean("vipDao", VipDao.class);
		// 增
		Vip vip = new Vip();
		vip.setName("孙悟空");
		vip.setBirth("1999-11-11");
		vip.setCardNumber("1234567894");
		vipMapper.insert(vip);
		// 查一个
		Vip vip1 = vipMapper.selectByPrimaryKey(2L);
		System.out.println(vip1);
		// 改
		vip1.setName("孙行者");
		vipMapper.updateByPrimaryKey(vip1);
		// 删
		vipMapper.deleteByPrimaryKey(1L);

		// 关闭Spring容器
		applicationContext.close();
	}

}
