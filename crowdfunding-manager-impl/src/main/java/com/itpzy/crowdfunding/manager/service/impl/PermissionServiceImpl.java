package com.itpzy.crowdfunding.manager.service.impl;

import com.itpzy.crowdfunding.bean.Permission;
import com.itpzy.crowdfunding.manager.dao.PermissionMapper;
import com.itpzy.crowdfunding.manager.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Permission queryParentPermission() {
      Permission parent =   permissionMapper.queryParentPermission();
        return parent;
    }

    @Override
    public List<Permission> queryPermissionByPid(Integer id) {
      List<Permission> list =   permissionMapper.queryPermissionByPid(id);
        return list;
    }

    @Override
    public List<Permission> queryAll() {
        return permissionMapper.selectAll();
    }

    @Override
    public List<Permission> queryAllIcons() {
        return permissionMapper.selectAllIcons();
    }

    @Override
    public int queryRepeatName(String name) {
        return permissionMapper.queryRepeatName(name);
    }

    @Override
    public int insertPermission(Permission permission) {
        return permissionMapper.insert(permission);
    }

    @Override
    public int updatePermission(Permission permission) {
        return permissionMapper.updateByPrimaryKey(permission);
    }

    @Override
    public int deletePermission(Integer id) {
        return permissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Permission> queryPermissionByUser(Integer id) {
        return permissionMapper.queryPermissionByUser(id);
    }

    @Override
    public List<String> queryAllUrl() {

        return permissionMapper.queryAllUrl();
    }
}
