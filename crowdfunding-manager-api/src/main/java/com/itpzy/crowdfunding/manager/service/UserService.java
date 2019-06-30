package com.itpzy.crowdfunding.manager.service;

import com.itpzy.crowdfunding.bean.User;

import java.util.Map;

public interface UserService {

    /**
     * 用户登录
     * @param usermap
     * @return
     */
    User selectUserDoLogin(Map usermap);
}
