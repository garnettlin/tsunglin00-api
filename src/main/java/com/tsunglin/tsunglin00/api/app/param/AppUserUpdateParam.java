package com.tsunglin.tsunglin00.api.app.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * 用戶修改param
 */
@Data
public class AppUserUpdateParam implements Serializable {

    @ApiModelProperty("用戶暱稱")
    private String nickName;

    @ApiModelProperty("用戶密碼(需要MD5加密)")
    private String passwordMd5;

    @ApiModelProperty("個性簽名")
    private String introduceSign;

}
