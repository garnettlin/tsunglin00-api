/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package com.tsunglin.tsunglin00.api.admin.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AdminCarouselAddParam {

    @ApiModelProperty("輪播圖URL地址")
    @NotEmpty(message = "輪播圖URL不能為空")
    private String carouselUrl;

    @ApiModelProperty("輪播圖跳轉地址")
    @NotEmpty(message = "輪播圖跳轉地址不能為空")
    private String redirectUrl;

    @ApiModelProperty("排序值")
    @Min(value = 1, message = "carouselRank最低為1")
    @Max(value = 200, message = "carouselRank最高為200")
    @NotNull(message = "carouselRank不能為空")
    private Integer carouselRank;
}
