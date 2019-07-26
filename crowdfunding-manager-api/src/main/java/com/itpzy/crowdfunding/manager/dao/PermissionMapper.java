package com.itpzy.crowdfunding.manager.dao;

import com.itpzy.crowdfunding.bean.Permission;

import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Integer id);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);

    //查询根节点 pid=null
    Permission queryParentPermission();

    //根据Pid查询permission
    List<Permission> queryPermissionByPid(Integer id);

    //查询所有不重复的icon
    List<Permission> selectAllIcons();

    int queryRepeatName(String name);

    //根据用户id 查询出所有的权限
    List<Permission> queryPermissionByUser(Integer id);

    //查询出所有的权限路径
    List<String> queryAllUrl();

}