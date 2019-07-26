package com.itpzy.crowdfunding.controller;

import com.itpzy.crowdfunding.bean.Permission;
import com.itpzy.crowdfunding.bean.User;
import com.itpzy.crowdfunding.manager.service.PermissionService;
import com.itpzy.crowdfunding.manager.service.UserService;
import com.itpzy.crowdfunding.util.AjaxResult;
import com.itpzy.crowdfunding.util.Const;
import com.itpzy.crowdfunding.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class DispatcherControl {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;



    @RequestMapping(value = "/loginout")
    public String loginout(HttpSession session){
        session.invalidate();//当退出后清除session域中的user数据
        return "redirect:/index.htm";//采用重定向的方式跳转到主页面，可防止原页面重复提交，即使刷新也是刷新index.htm
    }


    /**
     * 登录后跳转到后台页面
     * @return
     */
    @RequestMapping(value = "/main")
    public String doLoginMain(HttpSession session){

        User user = (User)session.getAttribute(Const.LOGIN_USER);
        //1.根节点集合
        List<Permission> root = new ArrayList<>();
        //2.查询出该用户所对应的所有权限
        List<Permission> permissions = permissionService.queryPermissionByUser(user.getId());
        //3.将查询出的权限都封装到Map集合中
        Map<Integer,Permission> permissionMap = new HashMap<>();
        for (Permission permission : permissions) {
            permissionMap.put(permission.getId(),permission);
        }
        //4.遍历权限集合，配对关系
        for (Permission permission : permissions) {
            if(permission.getPid()==null){
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

        session.setAttribute(Const.ROOT_PERMISSION,root);
        return "main";
    }


    /**
     * 异步：用户登录方法
     * @return
     */
    @RequestMapping(value="/doLogin",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult doLogin(String loginacct, String userpswd, String type, HttpSession session) {
        Map<String,Object> userMap = new HashMap<>();
        userMap.put("loginacct",loginacct);
        userMap.put("userpswd", MD5Util.digest(userpswd));
        userMap.put("type",type);
       try{
          User user = userService.selectUserDoLogin(userMap);
           session.setAttribute(Const.LOGIN_USER,user);

           List<Permission> permissions = permissionService.queryPermissionByUser(user.getId());
           Set<String> urls = new HashSet<>();
           for (Permission permission : permissions) {
               String url = permission.getUrl();
               urls.add("/"+url);
           }
           session.setAttribute("permissions",urls);


           return AjaxResult.success("执行成功!");
       }catch (Exception e){
         e.printStackTrace();
           return AjaxResult.fail(e.getMessage());
       }
    }


    /**
     * 同步：用户登录方法
     * @return
     */
 /*   @RequestMapping(value="/doLogin")
    public String doLogin(String loginacct, String userpswd, String type, HttpSession session) {
           Map<String,Object> userMap = new HashMap<>();
           userMap.put("loginacct",loginacct);
           userMap.put("userpswd",userpswd);
           userMap.put("type",type);
           User user = userService.selectUserDoLogin(userMap);
           session.setAttribute(Const.LOGIN_USER,user);
           return "redirect:main.htm";
    }*/

    /**
     * 跳转到首页方法
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }

    /**
     * 跳转到登录页面
     *
     * @return
     */
    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    /**
     * 跳转到注册页面
     *
     * @return
     */
    @RequestMapping(value = "/reg")
    public String reg() {
        return "reg";
    }

}
