package com.itpzy.crowdfunding.manager.service;

import com.itpzy.crowdfunding.bean.Permission;

import java.util.List;

public interface PermissionService {

    //查询根节点 Pid=null
    Permission queryParentPermission();

    //根据pid查询Permission
    List<Permission> queryPermissionByPid(Integer id);

    //查找所有得Permission
    List<Permission> queryAll();

    //查询所有不重复的icons
    List<Permission> queryAllIcons();

    //查询权限名称的个数
    int queryRepeatName(String name);
    //添加权限
    int insertPermission(Permission permission);

    //修改权限
    int updatePermission(Permission permission);

    //根据id删除权限
    int deletePermission(Integer id);

    //根据用户id查询出所对应的所有权限
    List<Permission> queryPermissionByUser(Integer id);

    //查询出所有的权限路径
    List<String> queryAllUrl();

}
