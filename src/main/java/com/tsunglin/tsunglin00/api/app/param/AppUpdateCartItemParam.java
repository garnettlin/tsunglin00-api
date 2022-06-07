package com.tsunglin.tsunglin00.api.app.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 修改購物項param
 */
@Data
public class AppUpdateCartItemParam implements Serializable {

    @ApiModelProperty("購物項id")
    private Long cartItemId;

    @ApiModelProperty("商品數量")
    private Integer goodsCount;
}
