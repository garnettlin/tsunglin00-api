/**
 * 嚴肅聲明：
 * 開源版本請務必保留此註釋頭信息，若刪除我方將保留所有法律責任追究！
 * 本系統已申請軟件著作權，受國家版權局知識產權以及國家計算機軟件著作權保護！
 * 可正常分享和學習源碼，不得用於違法犯罪活動，違者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版權所有，侵權必究！
 */
package com.tsunglin.tsunglin00.api.admin;

import com.tsunglin.tsunglin00.api.admin.param.AdminBatchIdParam;
import com.tsunglin.tsunglin00.config.annotation.TokenToAdminUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.tsunglin.tsunglin00.entity.AdminUserToken;
import com.tsunglin.tsunglin00.service.UserService;
import com.tsunglin.tsunglin00.util.PageQueryUtil;
import com.tsunglin.tsunglin00.util.Result;
import com.tsunglin.tsunglin00.util.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@RestController
@Api(value = "v1", tags = "後台-管理系統註冊用戶")
@RequestMapping("/manage-api/v1")
public class AdminRegisteUserAPI {

    private static final Logger logger = LoggerFactory.getLogger(AdminRegisteUserAPI.class);

    @Resource
    private UserService userService;

    /**
     * 列表
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ApiOperation(value = "商城註冊用戶列表", notes = "商城註冊用戶列表")
    public Result list(@RequestParam(required = false) @ApiParam(value = "頁碼") Integer pageNumber,
                       @RequestParam(required = false) @ApiParam(value = "每頁條數") Integer pageSize,
                       @RequestParam(required = false) @ApiParam(value = "用戶狀態") Integer lockStatus, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("users list adminUser:{}", adminUser.toString());
        if (pageNumber == null || pageNumber < 1 || pageSize == null || pageSize < 10) {
            return ResultGenerator.genFailResult("參數異常！");
        }
        Map params = new HashMap(8);
        params.put("page", pageNumber);
        params.put("limit", pageSize);
        if (lockStatus != null) {
            params.put("orderStatus", lockStatus);
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(userService.getNewBeeMallUsersPage(pageUtil));
    }

    /**
     * 用戶禁用與解除禁用(0-未鎖定 1-已鎖定)
     */
    @RequestMapping(value = "/users/{lockStatus}", method = RequestMethod.PUT)
    @ApiOperation(value = "修改用戶狀態", notes = "批量修改，用戶禁用與解除禁用(0-未鎖定 1-已鎖定)")
    public Result lockUser(@RequestBody AdminBatchIdParam adminBatchIdParam, @PathVariable int lockStatus, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("users lockUser adminUser:{}", adminUser.toString());
        if (adminBatchIdParam ==null|| adminBatchIdParam.getIds().length < 1) {
            return ResultGenerator.genFailResult("參數異常！");
        }
        if (lockStatus != 0 && lockStatus != 1) {
            return ResultGenerator.genFailResult("操作非法！");
        }
        if (userService.lockUsers(adminBatchIdParam.getIds(), lockStatus)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("禁用失敗");
        }
    }
}