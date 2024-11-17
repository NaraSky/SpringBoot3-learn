package com.lb.mybatis.repository;

import com.lb.mybatis.model.Vip;

import java.util.List;

public interface    VipMapper {
    /**
     * 插入会员信息
     * @param vip
     * @return 1表示插入成功，其他值表示失败
     */
    int insert(Vip vip);

    /**
     * 根据id删除会员信息
     * @param id 会员唯一标识
     * @return 1表示删除成功，其他值表示失败
     */
    int deleteById(Long id);

    /**
     * 更新会员信息（id不可更新）
     * @param vip 会员信息
     * @return 1表示更新成功，其他值表示更新失败。
     */
    int update(Vip vip);

    /**
     * 根据id查询会员信息
     * @param id 会员的唯一标识
     * @return 会员信息
     */
    Vip selectById(Long id);

    /**
     * 获取所有会员信息
     * @return
     */
    List<Vip> selectAll();
}