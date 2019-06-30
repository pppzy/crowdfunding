package com.itpzy.crowdfunding.manager.service.impl;

import com.itpzy.crowdfunding.bean.User;
import com.itpzy.crowdfunding.manager.dao.UserMapper;
import com.itpzy.crowdfunding.manager.service.UserService;
import com.itpzy.crowdfunding.util.DoLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectUserDoLogin(Map usermap) {
        User user = userMapper.selectUserdoLogin(usermap);
        if(user==null){
            int i = userMapper.selectCountUser(usermap);
            if(i!=1){
                throw new DoLoginException("用户不存在!");
            }else{
                throw new DoLoginException("用户名密码不正确!");
            }
        }
        return user;
    }
}
