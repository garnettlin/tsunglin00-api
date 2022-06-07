package com.tsunglin.tsunglin00.api.admin.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AdminGoodsAddParam {

    @ApiModelProperty("商品名稱")
    @NotEmpty(message = "商品名稱不能為空")
    @Length(max = 128,message = "商品名稱內容過長")
    private String goodsName;

    @ApiModelProperty("商品簡介")
    @NotEmpty(message = "商品簡介不能為空")
    @Length(max = 200,message = "商品簡介內容過長")
    private String goodsIntro;

    @ApiModelProperty("分類id")
    @NotNull(message = "分類id不能為空")
    @Min(value = 1, message = "分類id最低為1")
    private Long goodsCategoryId;

    @ApiModelProperty("商品主圖")
    @NotEmpty(message = "商品主圖不能為空")
    private String goodsCoverImg;

    @ApiModelProperty("originalPrice")
    @NotNull(message = "originalPrice不能為空")
    @Min(value = 1, message = "originalPrice最低為1")
    @Max(value = 1000000, message = "originalPrice最高為1000000")
    private Integer originalPrice;

    @ApiModelProperty("sellingPrice")
    @NotNull(message = "sellingPrice不能為空")
    @Min(value = 1, message = "sellingPrice最低為1")
    @Max(value = 1000000, message = "sellingPrice最高為1000000")
    private Integer sellingPrice;

    @ApiModelProperty("庫存")
    @NotNull(message = "庫存不能為空")
    @Min(value = 1, message = "庫存最低為1")
    @Max(value = 100000, message = "庫存最高為100000")
    private Integer stockNum;

    @ApiModelProperty("商品標籤")
    @NotEmpty(message = "商品標籤不能為空")
    @Length(max = 16,message = "商品標籤內容過長")
    private String tag;

    private Byte goodsSellStatus;

    @ApiModelProperty("商品詳情")
    @NotEmpty(message = "商品詳情不能為空")
    private String goodsDetailContent;
}