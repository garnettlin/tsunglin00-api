package com.tsunglin.tsunglin00.api.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品詳情頁VO
 */
@Data
public class AppGoodsDetailVO implements Serializable {

    @ApiModelProperty("商品id")
    private Long goodsId;

    @ApiModelProperty("商品名稱")
    private String goodsName;

    @ApiModelProperty("商品簡介")
    private String goodsIntro;

    @ApiModelProperty("商品圖片地址")
    private String goodsCoverImg;

    @ApiModelProperty("商品價格")
    private Integer sellingPrice;

    @ApiModelProperty("商品標籤")
    private String tag;

    @ApiModelProperty("商品圖片")
    private String[] goodsCarouselList;

    @ApiModelProperty("商品原價")
    private Integer originalPrice;

    @ApiModelProperty("商品詳情字段")
    private String goodsDetailContent;
}
