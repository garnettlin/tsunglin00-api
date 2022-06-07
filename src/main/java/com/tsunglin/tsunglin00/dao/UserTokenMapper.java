package com.tsunglin.tsunglin00.dao;

import com.tsunglin.tsunglin00.entity.UserToken;

public interface UserTokenMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(UserToken record);

    int insertSelective(UserToken record);

    UserToken selectByPrimaryKey(Long userId);

    UserToken selectByToken(String token);

    int updateByPrimaryKeySelective(UserToken record);

    int updateByPrimaryKey(UserToken record);
}