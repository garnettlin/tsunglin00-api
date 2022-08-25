package com.tsunglin.tsunglin00.api.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 購物車頁面購物項VO
 */
@Data
public class AppShoppingCartItemVO implements Serializable {

    @ApiModelProperty("購物項id")
    private Long cartItemId;

    @ApiModelProperty("商品id")
    private Long goodsId;

    @ApiModelProperty("商品數量")
    private Integer goodsCount;

    @ApiModelProperty("商品名稱")
    private String goodsName;

    @ApiModelProperty("商品圖片")
    private String goodsCoverImg;

    @ApiModelProperty("商品價格")
    private Integer sellingPrice;
}
