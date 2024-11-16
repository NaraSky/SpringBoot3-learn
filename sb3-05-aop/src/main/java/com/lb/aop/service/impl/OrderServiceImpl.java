package com.lb.aop.service.impl;

import com.lb.aop.service.OrderService;
import org.springframework.stereotype.Service;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Override
    public void generate() {
        System.out.println("生成订单");
    }

    @Override
    public void detail() {
        System.out.println("订单详情");
    }
}