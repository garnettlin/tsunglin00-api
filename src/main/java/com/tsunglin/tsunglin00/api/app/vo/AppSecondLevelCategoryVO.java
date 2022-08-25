package com.tsunglin.tsunglin00.api.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 首頁分類數據VO(第二級)
 */
@Data
public class AppSecondLevelCategoryVO implements Serializable {

    @ApiModelProperty("當前二級分類id")
    private Long categoryId;

    @ApiModelProperty("父級分類id")
    private Long parentId;

    @ApiModelProperty("當前分類級別")
    private Byte categoryLevel;

    @ApiModelProperty("當前二級分類名稱")
    private String categoryName;

    @ApiModelProperty("三級分類列表")
    private List<AppThirdLevelCategoryVO> appThirdLevelCategoryVOS;
}
