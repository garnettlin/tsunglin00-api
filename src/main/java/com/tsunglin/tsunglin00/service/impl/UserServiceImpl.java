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
            //????????????????????????token?????????
            String token = getNewToken(System.currentTimeMillis() + "", user.getUserId());
            UserToken userToken = userTokenMapper.selectByPrimaryKey(user.getUserId());
            //????????????
            Date now = new Date();
            //????????????
            //Date expireTime = new Date(now.getTime() + 2 * 24 * 3600 * 1000);//???????????? 48 ??????
            Date expireTime = new Date(now.getTime() + 1 * 1 * 3600 * 1000);//???????????? 1 ??????
            if (userToken == null) {
                userToken = new UserToken();
                userToken.setUserId(user.getUserId());
                userToken.setToken(token);
                userToken.setUpdateTime(now);
                userToken.setExpireTime(expireTime);
                //????????????token??????
                if (userTokenMapper.insertSelective(userToken) > 0) {
                    //?????????????????????
                    return token;
                }
            } else {
                userToken.setToken(token);
                userToken.setUpdateTime(now);
                userToken.setExpireTime(expireTime);
                //??????
                if (userTokenMapper.updateByPrimaryKeySelective(userToken) > 0) {
                    //?????????????????????
                    return token;
                }
            }

        }
        return ServiceResultEnum.LOGIN_ERROR.getResult();
    }

    /**
     * ??????token???
     *
     * @param timeStr
     * @param userId
     * @return
     */
    private String getNewToken(String timeStr, Long userId) {
        String src = timeStr + userId + NumberUtil.genRandomNum(4);
        return SystemUtil.genToken(src);
    }
    //  ??????????????????
    @Override
    public Boolean updateUserInfo(AppUserUpdateParam mallUser, Long userId) {
        //  ???????????????
        Users users = UserMapper.selectByPrimaryKey(userId);
        if (users == null) {
            Exception.fail(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        users.setNickName(mallUser.getNickName());
        //user.setPasswordMd5(mallUser.getPasswordMd5());
        //????????????????????????????????????????????????????????????????????????????????????
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
