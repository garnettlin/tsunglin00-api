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
