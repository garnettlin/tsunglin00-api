/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
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
