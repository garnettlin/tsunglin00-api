package com.tsunglin.tsunglin00.service.impl;

import com.tsunglin.tsunglin00.api.app.param.AppUserUpdateParam;
import com.tsunglin.tsunglin00.common.Constants;
import com.tsunglin.tsunglin00.common.Exception;
import com.tsunglin.tsunglin00.common.ServiceResultEnum;
import com.tsunglin.tsunglin00.dao.UserMapper;
import com.tsunglin.tsunglin00.dao.UserTokenMapper;
import com.tsunglin.tsunglin00.entity.Users;
import com.tsunglin.tsunglin00.entity.UserToken;
import com.tsunglin.tsunglin00.service.UserService;
import com.tsunglin.tsunglin00.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserMapper UserMapper;
    @Autowired
    private UserTokenMapper userTokenMapper;

    @Override
    public String register(String loginName, String password) {
        if (UserMapper.selectByLoginName(loginName) != null) {
            logger.info("register={}", UserMapper.selectByLoginName(loginName));
            return ServiceResultEnum.SAME_LOGIN_NAME_EXIST.getResult();
        }
        Users registerUser = new Users();
        registerUser.setLoginName(loginName);
        registerUser.setNickName(loginName);
        registerUser.setIntroduceSign(Constants.USER_INTRO);
        String passwordMD5 = MD5Util.MD5Encode(password, "UTF-8");
        registerUser.setPasswordMd5(passwordMD5);
        if (UserMapper.insertSelective(registerUser) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String login(String loginName, String passwordMD5) {
        Users user = UserMapper.selectByLoginNameAndPasswd(loginName, passwordMD5);
        if (user != null) {
            if (user.getLockedFlag() == 1) {
                return ServiceResultEnum.LOGIN_USER_LOCKED_ERROR.getResult();
            }
            //登錄後即執行修改token的操作
            String token = getNewToken(System.currentTimeMillis() + "", user.getUserId());
            UserToken userToken = userTokenMapper.selectByPrimaryKey(user.getUserId());
            //當前時間
            Date now = new Date();
            //過期時間
            //Date expireTime = new Date(now.getTime() + 2 * 24 * 3600 * 1000);//過期時間 48 小時
            Date expireTime = new Date(now.getTime() + 1 * 1 * 3600 * 1000);//過期時間 1 小時
            if (userToken == null) {
                userToken = new UserToken();
                userToken.setUserId(user.getUserId());
                userToken.setToken(token);
                userToken.setUpdateTime(now);
                userToken.setExpireTime(expireTime);
                //新增一條token數據
                if (userTokenMapper.insertSelective(userToken) > 0) {
                    //新增成功後返回
                    return token;
                }
            } else {
                userToken.setToken(token);
                userToken.setUpdateTime(now);
                userToken.setExpireTime(expireTime);
                //更新
                if (userTokenMapper.updateByPrimaryKeySelective(userToken) > 0) {
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
        String src = timeStr + userId + NumberUtil.genRandomNum(4);
        return SystemUtil.genToken(src);
    }
    //  修改用戶信息
    @Override
    public Boolean updateUserInfo(AppUserUpdateParam mallUser, Long userId) {
        //  取得使用者
        Users users = UserMapper.selectByPrimaryKey(userId);
        if (users == null) {
            Exception.fail(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        users.setNickName(mallUser.getNickName());
        //user.setPasswordMd5(mallUser.getPasswordMd5());
        //若密碼為空字符，則表明用戶不打算修改密碼，使用原密碼保存
        if (!MD5Util.MD5Encode("", "UTF-8").equals(mallUser.getPasswordMd5())){
            users.setPasswordMd5(mallUser.getPasswordMd5());
        }
        users.setIntroduceSign(mallUser.getIntroduceSign());
        if (UserMapper.updateByPrimaryKeySelective(users) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean logout(Long userId) {
        return userTokenMapper.deleteByPrimaryKey(userId) > 0;
    }

    @Override
    public PageResult getNewBeeMallUsersPage(PageQueryUtil pageUtil) {
        List<Users> Users = UserMapper.findUserList(pageUtil);
        int total = UserMapper.getTotalUsers(pageUtil);
        PageResult pageResult = new PageResult(Users, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public Boolean lockUsers(Long[] ids, int lockStatus) {
        if (ids.length < 1) {
            return false;
        }
        return UserMapper.lockUserBatch(ids, lockStatus) > 0;
    }
}
