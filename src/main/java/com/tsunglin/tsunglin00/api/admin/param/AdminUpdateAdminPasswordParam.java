package com.tsunglin.tsunglin00.api.admin.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AdminUpdateAdminPasswordParam {

    @NotEmpty(message = "originalPassword不能為空")
    private String originalPassword;

    @NotEmpty(message = "newPassword不能為空")
    private String newPassword;
}
