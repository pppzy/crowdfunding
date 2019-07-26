package com.itpzy.crowdfunding.manager.service.impl;

import com.itpzy.crowdfunding.bean.Role;
import com.itpzy.crowdfunding.manager.dao.RoleMapper;
import com.itpzy.crowdfunding.manager.service.RoleService;
import com.itpzy.crowdfunding.util.Page;
import com.itpzy.crowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;


    @Override
    public Page selectAllRoles(Page page, String queryText) {
        int pageno = page.getPageNo();
        int pagesize = page.getPageSize();
        int totalCount = 0;
        //总记录数
            totalCount = roleMapper.selectCountWithText(queryText);
        page.setTotalCount(totalCount);
        //总页数
        int totalPage = totalCount%pagesize==0?totalCount/pagesize:totalCount/pagesize+1;
        page.setTotalPage(totalPage);

        int startIndex = (pageno-1)*pagesize;
        page.setStartIndex(startIndex);
        List<Role> roleList = roleMapper.selectRole(page,queryText);

        page.setList(roleList);
        return page ;
    }

    @Override
    public List<Role> selectAll() {
        return roleMapper.selectAll();
    }

    @Override
    public List<Integer> selectRoleWithPermission(Integer rid) {
        return roleMapper.selectRoleWithPermission(rid);
    }

    @Override
    public int assignPermission(Data data,Integer roleid) {
        List<Integer> ids = data.getIds();
        int count =0;
        for (Integer id : ids) {
           int i =  roleMapper.assignPermission(id,roleid);
            count++;
        }
        return count  ;
    }

    @Override
    public void deletePermissionById(Integer roleid) {
        roleMapper.deletePermissionById(roleid);
    }
}
