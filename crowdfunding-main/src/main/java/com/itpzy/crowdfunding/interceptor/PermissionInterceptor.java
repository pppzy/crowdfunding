package com.itpzy.crowdfunding.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public class PermissionInterceptor extends HandlerInterceptorAdapter {



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ServletContext servletContext = request.getSession().getServletContext();
        Set<String> userUrl = (Set<String>)servletContext.getAttribute("allPermissions");

        String servletPath = request.getServletPath();
        if(userUrl.contains(servletPath)){
              Set<String> urls =  (Set<String>)request.getSession().getAttribute("permissions");

              if(urls.contains(servletPath)){
                  return true;
              }else {
                  request.getSession().setAttribute("visitMsg","对不起该用户没有权限访问!");
                  response.sendRedirect(request.getContextPath()+"/main.htm");
                  return false;
              }
        }else{
            return true;
        }
    }
}
