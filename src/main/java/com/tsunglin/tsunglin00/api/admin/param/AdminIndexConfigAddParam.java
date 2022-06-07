package com.tsunglin.tsunglin00.api.admin.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AdminIndexConfigAddParam {

    @ApiModelProperty("配置項名稱")
    @NotEmpty(message = "configName不能為空")
    private String configName;

    @ApiModelProperty("配置類別")
    @NotNull(message = "configType不能為空")
    @Min(value = 1, message = "configType最小為1")
    @Max(value = 5, message = "configType最大為5")
    private Byte configType;

    @ApiModelProperty("商品id")
    @NotNull(message = "商品id不能為空")
    @Min(value = 1, message = "商品id不能為空")
    private Long goodsId;

    @ApiModelProperty("排序值")
    @Min(value = 1, message = "configRank最低為1")
    @Max(value = 200, message = "configRank最高為200")
    @NotNull(message = "configRank不能為空")
    private Integer configRank;
}