package com.tsunglin.tsunglin00.api.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AppUserVO implements Serializable {

    @ApiModelProperty("用戶暱稱")
    private String nickName;

    @ApiModelProperty("用戶登錄名")
    private String loginName;

    @ApiModelProperty("個性簽名")
    private String introduceSign;
}
