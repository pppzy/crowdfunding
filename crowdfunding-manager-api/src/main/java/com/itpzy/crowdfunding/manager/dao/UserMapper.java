package com.itpzy.crowdfunding.manager.dao;

import com.itpzy.crowdfunding.bean.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    //<!--登陆时查询用户方法 -->
    User selectUserdoLogin(Map usermap);
    //<!--查询该用户是否存在 -->
    int selectCountUser(Map usermap);
}