package com.itpzy.crowdfunding.interceptor;

import com.itpzy.crowdfunding.bean.Member;
import com.itpzy.crowdfunding.bean.User;
import com.itpzy.crowdfunding.util.Const;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Set<String> noLoingUrl = new HashSet<>();
        noLoingUrl.add("/login.htm");
        noLoingUrl.add("/reg.htm");
        noLoingUrl.add("/index.htm");
        noLoingUrl.add("/main.htm");
        noLoingUrl.add("/doLogin.do");

        if (noLoingUrl.contains(request.getServletPath())){
            return true;
        }else{
            User user = (User)request.getSession().getAttribute(Const.LOGIN_USER);
            Member member=(Member)request.getSession().getAttribute(Const.LOGIN_MEMBER);
            if(user!=null||member!=null){
                return true;
            }else{
                request.getSession().setAttribute("loginMsg","请登录用户后再访问!");
                response.sendRedirect(request.getContextPath()+"/login.htm");
                return false;
            }
        }
    }
}
