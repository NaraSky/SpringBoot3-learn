package com.lb.springboot3.repository;

import com.lb.springboot3.model.Vip;
import org.springframework.stereotype.Repository;

@Repository
public interface VipDao {
    int deleteByPrimaryKey(Long id);

    int insert(Vip record);

    int insertSelective(Vip record);

    Vip selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Vip record);

    int updateByPrimaryKey(Vip record);
}