package com.tsunglin.tsunglin00.dao;

import com.tsunglin.tsunglin00.entity.Users;
import com.tsunglin.tsunglin00.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(Users record);

    int insertSelective(Users record);
    //  取得使用者
    Users selectByPrimaryKey(Long userId);

    Users selectByLoginName(String loginName);

    Users selectByLoginNameAndPasswd(@Param("loginName") String loginName, @Param("password") String password);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    List<Users> findUserList(PageQueryUtil pageUtil);

    int getTotalUsers(PageQueryUtil pageUtil);

    int lockUserBatch(@Param("ids") Long[] ids, @Param("lockStatus") int lockStatus);
}