package com.tsunglin.tsunglin00.service.impl;

import com.tsunglin.tsunglin00.api.app.vo.AppUserAddressVO;
import com.tsunglin.tsunglin00.common.Exception;
import com.tsunglin.tsunglin00.common.ServiceResultEnum;
import com.tsunglin.tsunglin00.dao.UserAddressMapper;
import com.tsunglin.tsunglin00.entity.UserAddress;
import com.tsunglin.tsunglin00.service.UserAddressService;
import com.tsunglin.tsunglin00.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserAddressServiceImpl implements UserAddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Override
    public List<AppUserAddressVO> getMyAddresses(Long userId) {
        List<UserAddress> myAddressList = userAddressMapper.findMyAddressList(userId);
        List<AppUserAddressVO> appUserAddressVOS = BeanUtil.copyList(myAddressList, AppUserAddressVO.class);
        return appUserAddressVOS;
    }
    //  保存收貨地址
    @Override
    @Transactional
    public Boolean saveUserAddress(UserAddress userAddress) {
        Date now = new Date();
        if (userAddress.getDefaultFlag().intValue() == 1) {
            //添加默認地址，需要將原有的默認地址修改掉
            UserAddress defaultAddress = userAddressMapper.getMyDefaultAddress(userAddress.getUserId());
            if (defaultAddress != null) {
                defaultAddress.setDefaultFlag((byte) 0);
                defaultAddress.setUpdateTime(now);
                int updateResult = userAddressMapper.updateByPrimaryKeySelective(defaultAddress);
                if (updateResult < 1) {
                    //未更新成功
                    Exception.fail(ServiceResultEnum.DB_ERROR.getResult());
                }
            }
        }
        return userAddressMapper.insertSelective(userAddress) > 0;
    }
    //  修改收貨地址
    @Override
    public Boolean updateMallUserAddress(UserAddress userAddress) {
        UserAddress tempAddress = getMallUserAddressById(userAddress.getAddressId());
        Date now = new Date();
        if (userAddress.getDefaultFlag().intValue() == 1) {
            //修改為默認地址，需要將原有的默認地址修改掉
            UserAddress defaultAddress = userAddressMapper.getMyDefaultAddress(userAddress.getUserId());
            if (defaultAddress != null && !defaultAddress.getAddressId().equals(tempAddress)) {
                //存在默認地址且默認地址並不是當前修改的地址
                defaultAddress.setDefaultFlag((byte) 0);
                defaultAddress.setUpdateTime(now);
                int updateResult = userAddressMapper.updateByPrimaryKeySelective(defaultAddress);
                if (updateResult < 1) {
                    //未更新成功
                    Exception.fail(ServiceResultEnum.DB_ERROR.getResult());
                }
            }
        }
        userAddress.setUpdateTime(now);
        return userAddressMapper.updateByPrimaryKeySelective(userAddress) > 0;
    }
    //  獲取默認收貨地址
    @Override
    public UserAddress getMallUserAddressById(Long addressId) {
        UserAddress userAddress = userAddressMapper.selectByPrimaryKey(addressId);
        if (userAddress == null) {
            Exception.fail(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        return userAddress;
    }

    @Override
    public UserAddress getMyDefaultAddressByUserId(Long userId) {
        return userAddressMapper.getMyDefaultAddress(userId);
    }

    @Override
    public Boolean deleteById(Long addressId) {
        return userAddressMapper.deleteByPrimaryKey(addressId) > 0;
    }
}
