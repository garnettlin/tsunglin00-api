package com.tsunglin.tsunglin00.api.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 首頁輪播圖VO
 */
@Data
public class AppIndexCarouselVO implements Serializable {

    @ApiModelProperty("輪播圖圖片地址")
    private String carouselUrl;

    @ApiModelProperty("輪播圖點擊後的跳轉路徑")
    private String redirectUrl;
}