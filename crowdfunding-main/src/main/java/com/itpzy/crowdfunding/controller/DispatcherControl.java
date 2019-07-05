package com.itpzy.crowdfunding.controller;

import com.itpzy.crowdfunding.bean.User;
import com.itpzy.crowdfunding.manager.service.UserService;
import com.itpzy.crowdfunding.util.AjaxResult;
import com.itpzy.crowdfunding.util.Const;
import com.itpzy.crowdfunding.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class DispatcherControl {

    @Autowired
    private UserService userService;



    @RequestMapping(value = "/loginout")
    public String loginout(HttpSession session){
        session.removeAttribute(Const.LOGIN_USER);//当退出后清除session域中的user数据
        return "redirect:/index.htm";//采用重定向的方式跳转到主页面，可防止原页面重复提交，即使刷新也是刷新index.htm
    }


    /**
     * 登录后跳转到后台页面
     * @return
     */
    @RequestMapping(value = "/main")
    public String doLoginMain(){
        return "main";
    }


    /**
     * 异步：用户登录方法
     * @return
     */
    @RequestMapping(value="/doLogin")
    @ResponseBody
    public AjaxResult doLogin(String loginacct, String userpswd, String type, HttpSession session) {
        Map<String,Object> userMap = new HashMap<>();
        userMap.put("loginacct",loginacct);
        userMap.put("userpswd", MD5Util.digest(userpswd));
        userMap.put("type",type);
       try{
          User user = userService.selectUserDoLogin(userMap);
           session.setAttribute(Const.LOGIN_USER,user);
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
