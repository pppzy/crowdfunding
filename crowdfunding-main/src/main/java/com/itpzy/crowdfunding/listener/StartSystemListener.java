package com.itpzy.crowdfunding.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartSystemListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //1.用于将上下文路径存放到application域中
        ServletContext sc = servletContextEvent.getServletContext();
        String contextPath = sc.getContextPath();
        sc.setAttribute("APP_PATH",contextPath);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
