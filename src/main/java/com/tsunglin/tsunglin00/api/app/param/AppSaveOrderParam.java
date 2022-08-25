package com.tsunglin.tsunglin00.api.app.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 保存訂單param
 */
@Data
public class AppSaveOrderParam implements Serializable {

    @ApiModelProperty("訂單項id數組")
    private Long[] cartItemIds;

    @ApiModelProperty("地址id")
    private Long addressId;
}
