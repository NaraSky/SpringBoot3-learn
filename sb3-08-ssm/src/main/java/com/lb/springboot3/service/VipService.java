package com.lb.springboot3.service;

import com.lb.springboot3.model.Vip;

public interface VipService {
    /**
     * 根据id获取会员信息
     * @param id 会员标识
     * @return 会员信息
     */
    Vip getById(Long id);
}