/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package com.tsunglin.tsunglin00.api.app.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 用戶登錄param
 */
@Data
public class AppUserLoginParam implements Serializable {

    @ApiModelProperty("登錄名")
    @NotEmpty(message = "登錄名不能為空")
    private String loginName;

    @ApiModelProperty("用戶密碼(需要MD5加密)")
    @NotEmpty(message = "密碼不能為空")
    private String passwordMd5;
}
