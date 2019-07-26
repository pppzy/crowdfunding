package com.itpzy.crowdfunding.manager.controller;

import com.itpzy.crowdfunding.bean.Permission;
import com.itpzy.crowdfunding.manager.service.PermissionService;
import com.itpzy.crowdfunding.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    //跳转到permission页面
    @RequestMapping(value = "toIndex")
    public String toIndex(){
        return "permission/index";
    }


    //异步加载树结构
   /* @RequestMapping(value = "queryPermission")
    @ResponseBody
    public AjaxResult doPermission(){

        try{
            List<Permission> root = new ArrayList<>();
            //找出根节点
            Permission parent =  permissionService.queryParentPermission();
            root.add(parent);
            //找出根下的子节点
            List<Permission> childrenList = permissionService.queryPermissionByPid(parent.getId());
            //添加到根节点中
            parent.setChildren(childrenList);

            //遍历子节点，作为父节点找出他们的子节点
            for (Permission permission : childrenList) {
                List<Permission> list =   permissionService.queryPermissionByPid(permission.getId());
                permission.setChildren(list);
            }
            return AjaxResult.success("查询成功").add("ztree",root);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.fail(e.getMessage());
        }
    }*/

   //递归得方法进行加载树
   /* @RequestMapping(value = "queryPermission")
    @ResponseBody
    public AjaxResult doPermission(){

        try{
            List<Permission> root = new ArrayList<>();
            //找出根节点
            Permission parent =  permissionService.queryParentPermission();

            root.add(parent);
            //找出根下的子节点
            List<Permission> childrenList = permissionService.queryPermissionByPid(parent.getId());
            //添加到根节点中
            parent.setChildren(childrenList);

            //遍历子节点，作为父节点找出他们的子节点
            for (Permission permission : childrenList) {
                List<Permission> list =   permissionService.queryPermissionByPid(permission.getId());
                permission.setChildren(list);
            }
            return AjaxResult.success("查询成功").add("ztree",root);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.fail(e.getMessage());
        }
    }

    private void digui(Permission parent){
        List<Permission> permissions = permissionService.queryPermissionByPid(parent.getId());
        parent.setChildren(permissions);
        for (Permission permission : permissions) {
           digui(permission);
        }
    }*/


   //一次查询全部数据，循环加载
  /*  @RequestMapping(value = "queryPermission")
    @ResponseBody
    public AjaxResult doPermission(){

        try{
            //1.根
            List<Permission> root = new ArrayList<>();

            //3.一次查询全部得数据减少与数据库得交互次数
            List<Permission> permissions = permissionService.queryAll();
            //4.遍历permissions
            for (Permission permission : permissions) {
                if(permission.getPid()==null){
                    root.add(permission);
                }else {
                    int count = permissions.size();
                    for (Permission listPm : permissions) {
                        //如果根据pid能找到父节点得id,那么listPm就作为该节点得父节点
                        if(permission.getPid()==listPm.getId()){
                            listPm.getChildren().add(permission);
                            break;
                        }else{
                            count--;
                            if(count==0){
                                root.add(permission);
                            }
                        }
                    }
                }
            }

            return AjaxResult.success("查询成功").add("ztree",root);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.fail(e.getMessage());
        }
    }*/

    //一次查询全部数据，循环加载（改进）
    @RequestMapping(value = "queryPermission")
    @ResponseBody
    public AjaxResult doPermission(){

        try{
            //1.根
            List<Permission> root = new ArrayList<>();

            //2.一次查询全部得数据减少与数据库得交互次数
            List<Permission> permissions = permissionService.queryAll();
            //3.创建Map集合用于存放，以id为key
            Map<Integer,Permission> permissionMap = new HashMap<>();

            for (Permission permission : permissions) {
                permissionMap.put(permission.getId(),permission);
            }

            for (Permission permission : permissions) {
                if(permission.getPid()==null){
                    permission.setOpen(true);
                    root.add(permission);
                }else{
                    //4.直接根据pid从map集合中查找对应得父节点
                    Permission parent = permissionMap.get(permission.getPid());
                    //5.如果不为空，则说明父节点存在
                    if(parent!=null){
                        permission.setOpen(true);
                        parent.getChildren().add(permission);
                    }else{
                        //6.如果为空，则说明该节点是个根下得父节点
                        root.add(permission);
                    }

                }
            }
            return AjaxResult.success("查询成功").add("ztree",root);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.fail(e.getMessage());
        }
    }


    //跳转到权限添加页面
    @RequestMapping(value = "toAdd")
    public String toAdd(Map map){
        List<Permission> permissions = permissionService.queryAllIcons();
        map.put("list",permissions);
        return "permission/add";
    }

    //校验权限名称是否存在重复
    @RequestMapping(value="doRepeatCheck")
    @ResponseBody
    public AjaxResult doRepeatCheck(String name){
        try{
            int count  = permissionService.queryRepeatName(name);
            if(count<1){
                return AjaxResult.success();
            }else{
                return AjaxResult.fail("权限名已存在!");
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.fail(e.getMessage());
        }
    }

    //添加权限
    @RequestMapping(value = "doAddPermission")
    @ResponseBody
    public AjaxResult doAddPermission(Permission permission){
        try {
          int count =   permissionService.insertPermission(permission);
          if(count==1){
              return AjaxResult.success("添加成功!");
          }else{
              return AjaxResult.fail("添加失败!");
          }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.fail(e.getMessage());
        }
    }

    //跳转到权限修改页面
    @RequestMapping(value = "toEdit")
    public String toEdit(Map map){
        List<Permission> permissions = permissionService.queryAllIcons();
        map.put("list",permissions);
        return "permission/edit";
    }

    //修改权限
    @RequestMapping(value = "toEditPermission")
    @ResponseBody
    public AjaxResult toEditPermission(Permission permission){
        try {
            int count =   permissionService.updatePermission(permission);
            if(count==1){
                return AjaxResult.success("修改成功!");
            }else{
                return AjaxResult.fail("修改失败!");
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "toDelete")
    @ResponseBody
    public AjaxResult toDelete(Integer id){
        try {
            int count = permissionService.deletePermission(id);
            if(count==1){
                return AjaxResult.success("删除成功!");
            }else{
                return AjaxResult.fail("删除失败!");
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.fail(e.getMessage());
        }
    }

}
