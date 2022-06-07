/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package com.tsunglin.tsunglin00.api.app.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 修改收貨地址param
 */
@Data
public class AppUpdateMallUserAddressParam {

    @ApiModelProperty("地址id")
    private Long addressId;

    @ApiModelProperty("用戶id")
    private Long userId;

    @ApiModelProperty("收件人名稱")
    private String userName;

    @ApiModelProperty("收件人聯繫方式")
    private String userPhone;

    @ApiModelProperty("是否默認地址 0-不是 1-是")
    private Byte defaultFlag;

    @ApiModelProperty("省")
    private String provinceName;

    @ApiModelProperty("市")
    private String cityName;

    @ApiModelProperty("區/縣")
    private String regionName;

    @ApiModelProperty("詳細地址")
    private String detailAddress;
}
