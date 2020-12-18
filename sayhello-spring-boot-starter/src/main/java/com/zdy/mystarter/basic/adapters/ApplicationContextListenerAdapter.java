package com.zdy.mystarter.basic.adapters;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 监听类
 * Created by Jason on 2020/1/8.
 */
public class ApplicationContextListenerAdapter implements ServletContextListener {
    /**
     * ServletContext的初始化处理
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("ApplicationContextListenerAdapter...contextInitialized....");
    }

    /**
     * ServletContext的销毁处理
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("ApplicationContextListenerAdapter...contextDestroyed....");
    }
}
