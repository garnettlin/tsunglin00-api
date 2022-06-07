package com.tsunglin.tsunglin00.api.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 首頁分類數據VO
 */
@Data
public class AppIndexCategoryVO implements Serializable {

    @ApiModelProperty("當前一級分類id")
    private Long categoryId;

    @ApiModelProperty("當前分類級別")
    private Byte categoryLevel;

    @ApiModelProperty("當前一級分類名稱")
    private String categoryName;

    @ApiModelProperty("二級分類列表")
    private List<AppSecondLevelCategoryVO> appsecondLevelCategoryVOS;
}
