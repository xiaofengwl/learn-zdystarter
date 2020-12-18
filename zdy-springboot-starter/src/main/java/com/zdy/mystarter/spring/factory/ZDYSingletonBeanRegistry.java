package com.zdy.mystarter.spring.factory;

import org.springframework.lang.Nullable;

/**
 * TODO IOC容器学习·SingletonBeanRegistry
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/22 17:35
 * @desc
 */
public interface ZDYSingletonBeanRegistry {

    /**
     * 注册单例Bean
     * @param var1
     * @param var2
     */
    void registerSingleton(String var1, Object var2);

    @Nullable
    Object getSingleton(String var1);

    boolean containsSingleton(String var1);

    String[] getSingletonNames();

    int getSingletonCount();

    Object getSingletonMutex();
}
