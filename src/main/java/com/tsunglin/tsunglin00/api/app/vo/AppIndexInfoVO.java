package com.tsunglin.tsunglin00.api.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AppIndexInfoVO implements Serializable {

    @ApiModelProperty("輪播圖(列表)")
    private List<AppIndexCarouselVO> carousels;

    @ApiModelProperty("首頁熱銷商品(列表)")
    private List<AppIndexConfigGoodsVO> hotGoodses;

    @ApiModelProperty("首頁新品推薦(列表)")
    private List<AppIndexConfigGoodsVO> newGoodses;

    @ApiModelProperty("首頁推薦商品(列表)")
    private List<AppIndexConfigGoodsVO> recommendGoodses;
}
