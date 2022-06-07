package com.tsunglin.tsunglin00.api.admin.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AdminUpdateAdminNameParam {

    @NotEmpty(message = "loginUserName不能為空")
    private String loginUserName;

    @NotEmpty(message = "nickName不能為空")
    private String nickName;
}
