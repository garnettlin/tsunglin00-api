package com.tsunglin.tsunglin00.api.app.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 添加購物項param
 */
@Data
public class AppSaveCartItemParam implements Serializable {

    @ApiModelProperty("商品數量")
    private Integer goodsCount;

    @ApiModelProperty("商品id")
    private Long goodsId;
}
