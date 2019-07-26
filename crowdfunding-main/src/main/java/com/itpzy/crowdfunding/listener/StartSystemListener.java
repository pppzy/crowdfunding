package com.itpzy.crowdfunding.listener;

import com.itpzy.crowdfunding.bean.Permission;
import com.itpzy.crowdfunding.manager.service.PermissionService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StartSystemListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //1.用于将上下文路径存放到application域中
        ServletContext sc = servletContextEvent.getServletContext();
        String contextPath = sc.getContextPath();
        sc.setAttribute("APP_PATH",contextPath);

        //利用工具获取IOC容器
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sc);

        PermissionService permissionService = context.getBean(PermissionService.class);

        //查询出所有权限，并存放到set集合中，最后存于application域中
        List<Permission> permissions = permissionService.queryAll();

        Set<String> userUrl = new HashSet<>();
        for (Permission permission : permissions) {
            String url = permission.getUrl();
            userUrl.add("/"+url);
        }

        sc.setAttribute("allPermissions",userUrl);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
