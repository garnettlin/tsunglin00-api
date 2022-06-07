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
public class AdminIndexConfigEditParam {

    @ApiModelProperty("待修改配置id")
    @NotNull(message = "configId不能為空")
    @Min(value = 1, message = "configId不能為空")
    private Long configId;

    @ApiModelProperty("配置的名稱")
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