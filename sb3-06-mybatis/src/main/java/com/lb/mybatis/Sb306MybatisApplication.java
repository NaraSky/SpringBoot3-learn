package com.lb.mybatis;

import com.lb.mybatis.model.Vip;
import com.lb.mybatis.repository.VipMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@MapperScan(basePackages = "com.lb.mybatis.repository")
@SpringBootApplication
public class Sb306MybatisApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(Sb306MybatisApplication.class, args);
        VipMapper vipMapper = (VipMapper) configurableApplicationContext.getBean("vipMapper");
        Vip vip = vipMapper.selectById(1L);
        System.out.println(vip);

        // 添加会员信息
        Vip newVip = new Vip("杰克", "1234567892", "1999-11-10");
        vipMapper.insert(newVip);
        // 查询所有会员信息
        List<Vip> vips = vipMapper.selectAll();
        System.out.println(vips);
        // 修改会员信息
        vip.setName("zhangsan");
        vipMapper.update(vip);
        // 查询所有会员信息
        List<Vip> vips2 = vipMapper.selectAll();
        System.out.println(vips2);
        // 删除会员信息
        vipMapper.deleteById(1L);
        // 查询所有会员信息
        List<Vip> vips3 = vipMapper.selectAll();
        System.out.println(vips3);
        configurableApplicationContext.close();
    }

}
