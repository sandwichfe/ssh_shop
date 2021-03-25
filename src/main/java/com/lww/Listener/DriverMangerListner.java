package com.lww.Listener;

import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * 服务在启动和使用（切换数据源查询）中都没有问题，但在关闭服务的时候，会出现两个错误：
 * 1、数据库的（mysql和oracle）驱动无法释放。
 * 2、 The web application [] appears to have started a thread named [Abandoned connection cleanup thread] but has failed to stop it.  This is very likely to create a memory leak
 * 针对驱动无法释放的问题，可以通过设置ServlectContextListner，在服务停止前，释放所有的Driver
 */
public class DriverMangerListner implements ServletContextListener {
    private final static Logger logger = Logger.getLogger(DriverMangerListner.class);
    public void contextInitialized(ServletContextEvent sce) {

    }

    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("[DriverMangerListner]:-------DriverManager deregisterDriver start...");
        //com.mysql.jdbc.AbandonedConnectionCleanupThread.uncheckedShutdown();
        Enumeration<Driver> enumeration = DriverManager.getDrivers();
        while (enumeration.hasMoreElements()) {
            try {
                DriverManager.deregisterDriver(enumeration.nextElement());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        logger.debug("[DriverMangerListner]:-------DriverManager deregisterDriver end...");
    }
}