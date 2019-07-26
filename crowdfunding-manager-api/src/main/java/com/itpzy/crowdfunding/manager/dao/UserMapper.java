package com.itpzy.crowdfunding.manager.dao;

import com.itpzy.crowdfunding.bean.User;
import com.itpzy.crowdfunding.vo.Data;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    //登陆时查询用户方法
    User selectUserdoLogin(Map usermap);
    //查询该用户是否存在
    int selectCountUser(Map usermap);
    //分页查询用户
    List<User> selectPage(Map<String, Object> dataMap);
    //查询用户的总记录数
    int selectCount(Map<String, Object> dataMap);

    //同步查询过时方法
    List<User> selectPage(Integer startIndex, Integer pageSize);
    int selectCount();

    //查询检查账号是否重复
    int selectRepeatUser(String loginacct);

    //批量删除用户操作
    int deleteBatchUser(@Param("userdata") Data datas);

    //根据用户id查询对应的角色id
    List<Integer> selectRoleById(Integer id);

    //根据用户id给分配角色
    int doAssignRole(@Param("userId") Integer userId, @Param("datas") Data datas);

    //根据用户id移除用户下的角色
    int doAssignUnRole(@Param("userId") Integer userId, @Param("datas") Data datas);

    //为新增用户分配最基本的角色
    int doAssignBaseRole(@Param("userId") Integer id, @Param("roleId") int i);
}