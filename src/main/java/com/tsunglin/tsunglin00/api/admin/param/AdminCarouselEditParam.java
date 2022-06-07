package com.tsunglin.tsunglin00.api.admin.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AdminCarouselEditParam {

    @ApiModelProperty("待修改輪播圖id")
    @NotNull(message = "輪播圖id不能為空")
    @Min(1)
    private Integer carouselId;

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
