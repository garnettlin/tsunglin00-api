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
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AdminGoodsCategoryAddParam {

    @ApiModelProperty("分類層級")
    @NotNull(message = "categoryLevel不能為空")
    @Min(value = 1, message = "分類級別最低為1")
    @Max(value = 3, message = "分類級別最高為3")
    private Byte categoryLevel;

    @ApiModelProperty("父類id")
    @NotNull(message = "parentId不能為空")
    @Min(value = 0, message = "parentId最低為0")
    private Long parentId;

    @ApiModelProperty("分類名稱")
    @NotEmpty(message = "categoryName不能為空")
    @Length(max = 16,message = "分類名稱過長")
    private String categoryName;

    @ApiModelProperty("排序值")
    @Min(value = 1, message = "categoryRank最低為1")
    @Max(value = 200, message = "categoryRank最高為200")
    @NotNull(message = "categoryRank不能為空")
    private Integer categoryRank;
}