package com.tsunglin.tsunglin00.api.app.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 添加收貨地址param
 */
@Data
public class AppSaveMallUserAddressParam {

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
