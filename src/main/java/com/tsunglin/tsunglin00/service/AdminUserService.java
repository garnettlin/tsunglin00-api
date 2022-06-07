package com.tsunglin.tsunglin00.service;

import com.tsunglin.tsunglin00.entity.AdminUser;

public interface AdminUserService {

    /**
     * 登錄
     * @param userName
     * @param password
     * @return
     */
    String login(String userName, String password);

    /**
     * 獲取用戶信息
     *
     * @param loginUserId
     * @return
     */
    AdminUser getUserDetailById(Long loginUserId);

    /**
     * 修改當前登錄用戶的密碼
     *
     * @param loginUserId
     * @param originalPassword
     * @param newPassword
     * @return
     */
    Boolean updatePassword(Long loginUserId, String originalPassword, String newPassword);

    /**
     * 修改當前登錄用戶的名稱信息
     *
     * @param loginUserId
     * @param loginUserName
     * @param nickName
     * @return
     */
    Boolean updateName(Long loginUserId, String loginUserName, String nickName);

    /**
     * 登出接口
     * @param adminUserId
     * @return
     */
    Boolean logout(Long adminUserId);


}
