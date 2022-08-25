package com.tsunglin.tsunglin00.api.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 首頁分類數據VO(第三級)
 */
@Data
public class AppThirdLevelCategoryVO implements Serializable {

    @ApiModelProperty("當前三級分類id")
    private Long categoryId;

    @ApiModelProperty("當前分類級別")
    private Byte categoryLevel;

    @ApiModelProperty("當前三級分類名稱")
    private String categoryName;
}
