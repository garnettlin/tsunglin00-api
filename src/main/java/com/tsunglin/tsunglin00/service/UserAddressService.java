package com.tsunglin.tsunglin00.service;

import com.tsunglin.tsunglin00.api.app.vo.AppUserAddressVO;
import com.tsunglin.tsunglin00.entity.UserAddress;

import java.util.List;

public interface UserAddressService {

    /**
     * 獲取我的收貨地址
     *
     * @param userId
     * @return
     */
    List<AppUserAddressVO> getMyAddresses(Long userId);

    /**
     * 保存收貨地址
     *
     * @param userAddress
     * @return
     */
    Boolean saveUserAddress(UserAddress userAddress);

    /**
     * 修改收貨地址
     *
     * @param userAddress
     * @return
     */
    Boolean updateMallUserAddress(UserAddress userAddress);

    /**
     * 獲取收貨地址詳情
     *
     * @param addressId
     * @return
     */
    UserAddress getMallUserAddressById(Long addressId);

    /**
     * 獲取我的默認收貨地址
     *
     * @param userId
     * @return
     */
    UserAddress getMyDefaultAddressByUserId(Long userId);

    /**
     * 刪除收貨地址
     *
     * @param addressId
     * @return
     */
    Boolean deleteById(Long addressId);
}
