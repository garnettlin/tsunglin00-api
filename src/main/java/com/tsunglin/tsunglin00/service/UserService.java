package com.tsunglin.tsunglin00.service;

import com.tsunglin.tsunglin00.api.app.param.AppUserUpdateParam;
import com.tsunglin.tsunglin00.util.PageQueryUtil;
import com.tsunglin.tsunglin00.util.PageResult;

public interface UserService {

    /**
     * 用戶註冊
     *
     * @param loginName
     * @param password
     * @return
     */
    String register(String loginName, String password);


    /**
     * 登錄
     *
     * @param loginName
     * @param passwordMD5
     * @return
     */
    String login(String loginName, String passwordMD5);

    /**
     * 修改用戶信息
     *
     * @param mallUser
     * @return
     */
    Boolean updateUserInfo(AppUserUpdateParam mallUser, Long userId);

    /**
     * 登出接口
     * @param userId
     * @return
     */
    Boolean logout(Long userId);

    /**
     * 用戶禁用與解除禁用(0-未鎖定 1-已鎖定)
     *
     * @param ids
     * @param lockStatus
     * @return
     */
    Boolean lockUsers(Long[] ids, int lockStatus);

    /**
     * 後台分頁
     *
     * @param pageUtil
     * @return
     */
    PageResult getNewBeeMallUsersPage(PageQueryUtil pageUtil);
}
