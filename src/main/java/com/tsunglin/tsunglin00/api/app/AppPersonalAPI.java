package com.tsunglin.tsunglin00.api.app;

import com.tsunglin.tsunglin00.api.app.param.AppUserLoginParam;
import com.tsunglin.tsunglin00.api.app.param.AppUserRegisterParam;
import com.tsunglin.tsunglin00.api.app.param.AppUserUpdateParam;
import com.tsunglin.tsunglin00.api.app.vo.AppUserVO;
import com.tsunglin.tsunglin00.config.annotation.TokenToMallUser;
import com.tsunglin.tsunglin00.util.BeanUtil;
import com.tsunglin.tsunglin00.util.NumberUtil;
import com.tsunglin.tsunglin00.util.Result;
import com.tsunglin.tsunglin00.util.ResultGenerator;
import io.swagger.annotations.*;
import com.tsunglin.tsunglin00.common.Constants;
import com.tsunglin.tsunglin00.common.ServiceResultEnum;
import com.tsunglin.tsunglin00.entity.Users;
import com.tsunglin.tsunglin00.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@Api(value = "v1", tags = "前台-帳號")
@RequestMapping("/api/v1")
public class AppPersonalAPI {

    @Resource
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(AppPersonalAPI.class);

    @PostMapping("/user/login")
    @ApiOperation(value = "登錄", notes = "返回token")
    public Result<String> login(@RequestBody @Valid AppUserLoginParam appUserLoginParam) {
        if (!NumberUtil.isPhone(appUserLoginParam.getLoginName())){
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_IS_NOT_PHONE.getResult());
        }
        String loginResult = userService.login(appUserLoginParam.getLoginName(), appUserLoginParam.getPasswordMd5());

        logger.info("user login api,loginName={},loginResult={}", appUserLoginParam.getLoginName(), loginResult);

        //登錄成功
        if (!StringUtils.isEmpty(loginResult) && loginResult.length() == Constants.TOKEN_LENGTH) {
            Result result = ResultGenerator.genSuccessResult();
            result.setData(loginResult);
            return result;
        }
        //登錄失敗
        return ResultGenerator.genFailResult(loginResult);
    }


    @PostMapping("/user/logout")
    @ApiOperation(value = "登出", notes = "清除token")
    public Result<String> logout(@TokenToMallUser Users loginMallUser) {
        Boolean logoutResult = userService.logout(loginMallUser.getUserId());

        logger.info("user logout api,loginMallUser={}", loginMallUser.getUserId());

        //登出成功
        if (logoutResult) {
            return ResultGenerator.genSuccessResult();
        }
        //登出失敗
        return ResultGenerator.genFailResult("logout error");
    }


    @PostMapping("/user/register")
    @ApiOperation(value = "註冊", notes = "")
    public Result register(@RequestBody @Valid AppUserRegisterParam appUserRegisterParam) {
        if (!NumberUtil.isPhone(appUserRegisterParam.getLoginName())){
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_IS_NOT_PHONE.getResult());
        }
        String registerResult = userService.register(appUserRegisterParam.getLoginName(), appUserRegisterParam.getPassword());

        logger.info("user register api,loginName={},loginResult={}", appUserRegisterParam.getLoginName(), registerResult);

        //註冊成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(registerResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //註冊失敗
        return ResultGenerator.genFailResult(registerResult);
    }

    @PutMapping("/user/info")
    @ApiOperation(value = "修改", notes = "")
    public Result updateInfo(@RequestBody @ApiParam("用戶信息") AppUserUpdateParam appUserUpdateParam, @TokenToMallUser Users loginMallUser) {
        Boolean flag = userService.updateUserInfo(appUserUpdateParam, loginMallUser.getUserId());
        logger.info("user updateInfo api,loginMallUser={}", loginMallUser.getUserId());
        if (flag) {
            //返回成功
            Result result = ResultGenerator.genSuccessResult();
            return result;
        } else {
            //返回失敗
            Result result = ResultGenerator.genFailResult("修改失敗");
            return result;
        }
    }

    @GetMapping("/user/info")
    @ApiOperation(value = "獲取", notes = "")
    public Result<AppUserVO> getUserDetail(@TokenToMallUser Users loginMallUser) {
        logger.info("user getUserDetail api,loginMallUser={}", loginMallUser.getUserId());
        //已登錄則直接返回
        AppUserVO mallUserVO = new AppUserVO();
        BeanUtil.copyProperties(loginMallUser, mallUserVO);
        return ResultGenerator.genSuccessResult(mallUserVO);
    }
}
