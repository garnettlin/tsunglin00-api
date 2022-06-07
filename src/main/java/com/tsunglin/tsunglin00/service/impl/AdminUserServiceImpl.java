package com.tsunglin.tsunglin00.service.impl;

import com.tsunglin.tsunglin00.common.ServiceResultEnum;
import com.tsunglin.tsunglin00.dao.AdminUserMapper;
import com.tsunglin.tsunglin00.dao.AdminUserTokenMapper;
import com.tsunglin.tsunglin00.entity.AdminUser;
import com.tsunglin.tsunglin00.entity.AdminUserToken;
import com.tsunglin.tsunglin00.service.AdminUserService;
import com.tsunglin.tsunglin00.util.NumberUtil;
import com.tsunglin.tsunglin00.util.SystemUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    private AdminUserMapper adminUserMapper;

    @Resource
    private AdminUserTokenMapper adminUserTokenMapper;

    @Override
    public String login(String userName, String password) {
        AdminUser loginAdminUser = adminUserMapper.login(userName, password);
        if (loginAdminUser != null) {
            //登錄後即執行修改token的操作
            String token = getNewToken(System.currentTimeMillis() + "", loginAdminUser.getAdminUserId());
            //讀取表後對應欄位
            AdminUserToken adminUserToken = adminUserTokenMapper.selectByPrimaryKey(loginAdminUser.getAdminUserId());
            //當前時間
            Date now = new Date();
            //過期時間
            //Date expireTime = new Date(now.getTime() + 2 * 24 * 3600 * 1000);//過期時間 48 小時
            Date expireTime = new Date(now.getTime() + 1 * 1 * 3600 * 1000);//過期時間 1 小時
            if (adminUserToken == null) {
                adminUserToken = new AdminUserToken();
                adminUserToken.setAdminUserId(loginAdminUser.getAdminUserId());
                adminUserToken.setToken(token);
                adminUserToken.setUpdateTime(now);
                adminUserToken.setExpireTime(expireTime);
                //新增一條token數據
                if (adminUserTokenMapper.insertSelective(adminUserToken) > 0) {
                    //新增成功後返回
                    return token;
                }
            } else {
                adminUserToken.setToken(token);
                adminUserToken.setUpdateTime(now);
                adminUserToken.setExpireTime(expireTime);
                //更新
                if (adminUserTokenMapper.updateByPrimaryKeySelective(adminUserToken) > 0) {
                    //修改成功後返回
                    return token;
                }
            }

        }
        return ServiceResultEnum.LOGIN_ERROR.getResult();
    }


    /**
     * 獲取token值
     *
     * @param timeStr
     * @param userId
     * @return
     */
    private String getNewToken(String timeStr, Long userId) {
        String src = timeStr + userId + NumberUtil.genRandomNum(6);
        return SystemUtil.genToken(src);
    }


    @Override
    public AdminUser getUserDetailById(Long loginUserId) {
        return adminUserMapper.selectByPrimaryKey(loginUserId);
    }

    @Override
    public Boolean updatePassword(Long loginUserId, String originalPassword, String newPassword) {
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(loginUserId);
        //當前用戶非空才可以進行更改
        if (adminUser != null) {
            //比較原密碼是否正確
            if (originalPassword.equals(adminUser.getLoginPassword())) {
                //設置新密碼並修改
                adminUser.setLoginPassword(newPassword);
                if (adminUserMapper.updateByPrimaryKeySelective(adminUser) > 0 && adminUserTokenMapper.deleteByPrimaryKey(loginUserId) > 0) {
                    //修改成功且清空當前token則返回true
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Boolean updateName(Long loginUserId, String loginUserName, String nickName) {
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(loginUserId);
        //當前用戶非空才可以進行更改
        if (adminUser != null) {
            //設置新名稱並修改
            adminUser.setLoginUserName(loginUserName);
            adminUser.setNickName(nickName);
            if (adminUserMapper.updateByPrimaryKeySelective(adminUser) > 0) {
                //修改成功則返回true
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean logout(Long adminUserId) {
        return adminUserTokenMapper.deleteByPrimaryKey(adminUserId) > 0;
    }
}
