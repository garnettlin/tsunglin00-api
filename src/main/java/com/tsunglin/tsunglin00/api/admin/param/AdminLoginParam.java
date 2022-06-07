/**
 * 嚴肅聲明：
 * 開源版本請務必保留此註釋頭信息，若刪除我方將保留所有法律責任追究！
 * 本軟件已申請軟件著作權，受國家版權局知識產權以及國家計算機軟件著作權保護！
 * 可正常分享和學習源碼，不得用於違法犯罪活動，違者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版權所有，侵權必究！
 */
package com.tsunglin.tsunglin00.api.admin.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class AdminLoginParam implements Serializable {

    @ApiModelProperty("登錄名")
    @NotEmpty(message = "登錄名不能為空")
    private String userName;

    @ApiModelProperty("用戶密碼(需要MD5加密)")
    @NotEmpty(message = "密碼不能為空")
    private String passwordMd5;
}