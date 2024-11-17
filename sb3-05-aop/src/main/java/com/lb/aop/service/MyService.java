package com.lb.aop.service;

import org.springframework.stereotype.Service;

@Service
public class MyService {

    public void doSomething() {
        // 模拟一个耗时操作
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}