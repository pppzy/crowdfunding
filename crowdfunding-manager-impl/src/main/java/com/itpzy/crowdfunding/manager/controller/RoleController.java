package com.itpzy.crowdfunding.manager.controller;

import com.itpzy.crowdfunding.bean.Permission;
import com.itpzy.crowdfunding.manager.service.PermissionService;
import com.itpzy.crowdfunding.manager.service.RoleService;
import com.itpzy.crowdfunding.util.AjaxResult;
import com.itpzy.crowdfunding.util.Page;
import com.itpzy.crowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/role")
@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    //跳转到role/index.jsp 角色维护主页面
    @RequestMapping(value="/toIndex")
    public String toIndex(){
        return "/role/index";
    }

    //跳转到role/add.jsp 添加页面
    @RequestMapping(value = "/toAdd")
    public String toAdd(){
        return "/role/add";
    }


    //异步方式——查询角色信息
    @RequestMapping(value = "ajaxRolePage")
    @ResponseBody
    public AjaxResult ajaxRolePage( Page page, @RequestParam(required = false) String queryText){
        try {
          page  =   roleService.selectAllRoles(page,queryText);
          return AjaxResult.success("查询成功!").add("list",page);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.fail(e.getMessage());
        }
    }

    //跳转到设置角色权限页面
    @RequestMapping(value="toAssignPermission")
    public String toAssignPermission(){
        return "role/assignPermission";
    }

    //异步查询角色拥有的权限
    @RequestMapping(value = "showPermission")
    @ResponseBody
    public List showPermission(Integer rid){
        //1.查询出角色对应的所有权限的id
       List<Integer> permissions = roleService.selectRoleWithPermission(rid);
        //2.根集合
        List<Permission> root = new ArrayList<>();
        //3.一次性查询出所有的权限
        List<Permission> permissionList = permissionService.queryAll();

        //4.将所有权限存入到Map集合中
        Map<Integer,Permission> permissionMap = new HashMap<>();

        for (Permission permission : permissionList) {
            permissionMap.put(permission.getId(),permission);
        }

        //5.用于回显角色拥有的权限
        for (Permission permission : permissionList) {
            if(permissions.contains(permission.getId())){
                permission.setChecked(true);
            }
        }

        //5.用于设置节点之间关系
        for (Permission permission : permissionList) {
            if(permission.getPid()==null){
                permission.setOpen(true);
                root.add(permission);
            }else{
                permission.setOpen(true);
                Permission parent = permissionMap.get(permission.getPid());
                if(parent==null){
                    root.add(permission);
                }else{
                    parent.getChildren().add(permission);
                }

            }
        }

        return root;
    }


    @RequestMapping(value = "assignPermission")
    @ResponseBody
    public AjaxResult assignPermission(Data data,Integer roleid){
        try{
          //1.先删除该角色所对应的所有权限
          roleService.deletePermissionById(roleid);
          //2.然后再分配
          int count = roleService.assignPermission(data,roleid);
          if(count==data.getIds().size()){
              return AjaxResult.success("分配成功!");
          }else{
              return AjaxResult.fail("分配失败!");
          }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.fail(e.getMessage());
        }
    }
}
