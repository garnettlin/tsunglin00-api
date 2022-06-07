package com.tsunglin.tsunglin00.dao;

import com.tsunglin.tsunglin00.entity.UserAddress;

import java.util.List;

public interface UserAddressMapper {
    int deleteByPrimaryKey(Long addressId);

    int insert(UserAddress record);
    //  保存收貨地址
    int insertSelective(UserAddress record);
    //  獲取默認收貨地址
    UserAddress selectByPrimaryKey(Long addressId);

    UserAddress getMyDefaultAddress(Long userId);

    List<UserAddress> findMyAddressList(Long userId);
    //  修改收貨地址
    int updateByPrimaryKeySelective(UserAddress record);

    int updateByPrimaryKey(UserAddress record);
}