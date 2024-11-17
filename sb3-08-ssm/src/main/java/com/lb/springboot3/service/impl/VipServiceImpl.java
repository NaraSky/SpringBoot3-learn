package com.lb.springboot3.service.impl;

import com.lb.springboot3.model.Vip;
import com.lb.springboot3.repository.VipDao;
import com.lb.springboot3.service.VipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("vipService")
public class VipServiceImpl implements VipService {

    @Autowired
    private VipDao vipMapper;

    @Override
    public Vip getById(Long id) {
        return vipMapper.selectByPrimaryKey(id);
    }
}