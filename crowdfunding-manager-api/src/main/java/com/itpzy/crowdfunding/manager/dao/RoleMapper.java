package com.itpzy.crowdfunding.manager.dao;

import com.itpzy.crowdfunding.bean.Role;
import com.itpzy.crowdfunding.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    Role selectByPrimaryKey(Integer id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    //查询总的记录数
    int selectCount();

    int  selectCountWithText(@Param("queryText") String queryText);

    //查询角色信息
    List<Role> selectRole(@Param("page") Page page, @Param("text") String queryText);

    //查询角色对应的所有权限id
    List<Integer> selectRoleWithPermission(Integer rid);

    //根据id给角色添加权限
    int assignPermission(@Param("permissionid") Integer id,@Param("roleid") Integer roleid);

    //根据角色id，删除所对应的所有权限
    int deletePermissionById(Integer roleid);
}