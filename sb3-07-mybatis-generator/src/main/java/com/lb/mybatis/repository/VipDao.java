package com.lb.mybatis.repository;

import com.lb.mybatis.model.Vip;
import org.springframework.stereotype.Repository;

public interface VipDao {
    int deleteByPrimaryKey(Long id);

    int insert(Vip record);

    int insertSelective(Vip record);

    Vip selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Vip record);

    int updateByPrimaryKey(Vip record);
}