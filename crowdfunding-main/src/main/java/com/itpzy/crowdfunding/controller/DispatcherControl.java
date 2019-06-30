package com.itpzy.crowdfunding.controller;

import com.itpzy.crowdfunding.bean.User;
import com.itpzy.crowdfunding.manager.service.UserService;
import com.itpzy.crowdfunding.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class DispatcherControl {

    @Autowired
    private UserService userService;

    /**
     * 登录后跳转到后台页面
     * @return
     */
    @RequestMapping(value = "/main")
    public String doLoginMain(){
        return "main";
    }


    /**
     * 同步：用户登录方法
     * @return
     */
    @RequestMapping(value="/doLogin")
    public String doLogin(String loginacct, String userpswd, String type, HttpSession session) {
           Map<String,Object> userMap = new HashMap<>();
           userMap.put("loginacct",loginacct);
           userMap.put("userpswd",userpswd);
           userMap.put("type",type);
           User user = userService.selectUserDoLogin(userMap);
           session.setAttribute(Const.LOGIN_USER,user);
           return "redirect:main.htm";
    }

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
