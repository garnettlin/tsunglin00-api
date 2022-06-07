package com.tsunglin.tsunglin00.api.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 搜索列表頁商品VO
 */
@Data
public class AppSearchGoodsVO implements Serializable {

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

}
