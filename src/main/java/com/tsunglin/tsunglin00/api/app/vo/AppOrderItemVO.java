package com.tsunglin.tsunglin00.api.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 訂單詳情頁頁面訂單項VO
 */
@Data
public class AppOrderItemVO implements Serializable {

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
