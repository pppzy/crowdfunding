package com.itpzy.crowdfunding.manager.service;

import com.itpzy.crowdfunding.bean.User;
import com.itpzy.crowdfunding.util.Page;

import java.util.List;
import java.util.Map;

public interface UserService {

    /**
     * 用户登录
     * @param usermap
     * @return
     */
    User selectUserDoLogin(Map usermap);

    /**
     * 同步分页查询
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page selectPage(Integer pageNo, Integer pageSize);

    /**
     * 异步分页查询用户+模糊搜索查询
     * @param dataMap
     * @return
     */
    Page selectPage(Map<String, Object> dataMap);

    /**
     * 账户名是否重复
     * @param loginacct
     * @return
     */
    int selectAcctRepeat(String loginacct);

    int addUser(User user);

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    User selectUserById(Integer id);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 根据id删除单个用户
     * @param id
     * @return
     */
    int deleteUser(Integer id);

    /**
     * 根据id集合，删除多个用户
     * @param idList
     * @return
     */
    int deleteUserList(List<Integer> idList);
}
