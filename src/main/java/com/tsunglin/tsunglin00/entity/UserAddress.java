package com.tsunglin.tsunglin00.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserAddress {
    private Long addressId;

    private Long userId;

    private String userName;

    private String userPhone;

    private Byte defaultFlag;

    private String provinceName;

    private String cityName;

    private String regionName;

    private String detailAddress;

    private Byte isDeleted;

    private Date createTime;

    private Date updateTime;
}