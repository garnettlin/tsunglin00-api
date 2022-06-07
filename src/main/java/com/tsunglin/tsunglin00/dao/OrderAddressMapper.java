package com.tsunglin.tsunglin00.dao;

import com.tsunglin.tsunglin00.entity.OrderAddress;

public interface OrderAddressMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(OrderAddress record);
    //  寫入主訂單ID及地址
    int insertSelective(OrderAddress record);

    OrderAddress selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(OrderAddress record);

    int updateByPrimaryKey(OrderAddress record);
}