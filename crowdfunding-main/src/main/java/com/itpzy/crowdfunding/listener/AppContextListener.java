package com.itpzy.crowdfunding.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.DriverManager;
import java.util.Set;

public class AppContextListener implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent event)  {
        try{
            while(DriverManager.getDrivers().hasMoreElements()){
                DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement());
                destroyThreads();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void contextInitialized(ServletContextEvent event)  {
        ServletContext context = event.getServletContext();
        String rootPath = context.getRealPath("/");
        System.setProperty("rootPath", rootPath);

        //logger.info("global setting,rootPath:{}",rootPath);
        //logger.info("deployed on architecture:{},operation System:{},version:{}",
        //       System.getProperty("os.arch"), System.getProperty("os.name"),
        //   System.getProperty("os.version"));
        //logger.info("app startup completed....");
    }

    private void destroyThreads(){
        final Set<Thread> threads = Thread.getAllStackTraces().keySet();
        for (Thread thread : threads) {
            if(thread.getName().equals("MySQL Statement Cancellation Timer")){
                synchronized (this) {
                    try {
                        thread.stop();
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
