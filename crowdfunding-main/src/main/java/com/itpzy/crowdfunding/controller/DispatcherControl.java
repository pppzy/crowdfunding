package com.itpzy.crowdfunding.controller;

import com.itpzy.crowdfunding.bean.Member;
import com.itpzy.crowdfunding.bean.Permission;
import com.itpzy.crowdfunding.bean.User;
import com.itpzy.crowdfunding.manager.service.PermissionService;
import com.itpzy.crowdfunding.manager.service.UserService;
import com.itpzy.crowdfunding.potal.service.MemberService;
import com.itpzy.crowdfunding.util.AjaxResult;
import com.itpzy.crowdfunding.util.Const;
import com.itpzy.crowdfunding.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class DispatcherControl {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private MemberService memberService;



    @RequestMapping(value = "/loginout")
    public String loginout(HttpSession session,HttpServletRequest request){
        session.invalidate();//当退出后清除session域中的user数据
        return "redirect:/index.htm";//采用重定向的方式跳转到主页面，可防止原页面重复提交，即使刷新也是刷新index.htm
    }


    //登录后跳转到前台会员主页面
    @RequestMapping(value = "memberIndex")
    public String memberIndex(){
        return "member/index";
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
    @RequestMapping(value="/doLogin",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult doLogin(String loginacct, String userpswd, String type, Integer rememberStatus, HttpSession session
            , HttpServletResponse response
            , HttpServletRequest request) {

        Map<String,Object> userMap = new HashMap<>();
        String value = "";
        String pswd = "";
        pswd= MD5Util.digest(userpswd);
        userMap.put("loginacct",loginacct);
        userMap.put("userpswd",pswd);

       try{

           if("user".equals(type)){
               LoginUser(userMap,session);
           }else if("member".equals(type)){
             Member member =  memberService.selectUserDoLogin(userMap);
             session.setAttribute(Const.LOGIN_MEMBER,member);
           }
           if(rememberStatus==1){
               String context = loginacct+"&"+pswd+"&"+type;
               Cookie cookie = new Cookie("loginCookie",context);
               cookie.setMaxAge(60*60*24*14);
               cookie.setPath("/");
               response.addCookie(cookie);
           }
           return AjaxResult.success("登录成功!");
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
    public String login(HttpServletRequest request,HttpSession session) {


        Map<String,Object> userMap = new HashMap<>();
        String value = "";
        String pswd = "";
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if("loginCookie".equals(cookie.getName())){
                value = cookie.getValue();
                break;
            }
        }
        if (value!=null&&value!="") {
            String[] split = value.split("&");
            String login = split[0];
            String pswd1 = split[1];
            String type = split[2];
            userMap.put("loginacct", login);
            userMap.put("userpswd", pswd1);

            if("user".equals(type)){
                LoginUser(userMap,session);
                return "main";
            }else if("member".equals(type)){
                Member member =  memberService.selectUserDoLogin(userMap);
                session.setAttribute(Const.LOGIN_MEMBER,member);
                return "member/index";
            }

        }else{
            return "login";
        }

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


    public void LoginUser(Map<String,Object> userMap,HttpSession session){
        User user = userService.selectUserDoLogin(userMap);
        session.setAttribute(Const.LOGIN_USER,user);

        //1.根节点集合
        List<Permission> root = new ArrayList<>();

        Map<Integer,Permission> permissionMap = new HashMap<>();
        //2.查询出该用户所对应的所有权限
        List<Permission> permissions = permissionService.queryPermissionByUser(user.getId());


        Set<String> urls = new HashSet<>();
        for (Permission permission : permissions) {
            //3.将查询出的权限都封装到Map集合中
            permissionMap.put(permission.getId(),permission);
            //封装该角色拥有的权限的路径到 urls 集合中
            String url = permission.getUrl();
            urls.add("/"+url);
        }

        //4.遍历权限集合，配对关系
        for (Permission permission : permissions) {
            if(permission.getPid()==null){
                root.add(permission);
            }else{
                Permission parent = permissionMap.get(permission.getPid());
                if(parent==null){
                    root.add(permission);
                }
                parent.getChildren().add(permission);
            }
        }

        session.setAttribute(Const.ROOT_PERMISSION,root);
        session.setAttribute("permissions",urls);
    }

}
