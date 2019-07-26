package com.itpzy.crowdfunding.manager.service;

import com.itpzy.crowdfunding.bean.Role;
import com.itpzy.crowdfunding.util.Page;
import com.itpzy.crowdfunding.vo.Data;

import java.util.List;

public interface RoleService {

    //根据分页查询所有角色
    Page selectAllRoles(Page page, String queryText);

    List<Role>  selectAll();

    //用于查询角色对应的权限的所有id
    List<Integer> selectRoleWithPermission(Integer rid);

    //根据权限id，给角色分配权限
    int assignPermission(Data data,Integer roleid);

    //根据角色id，删除角色所对应的权限
    void deletePermissionById(Integer roleid);
}
