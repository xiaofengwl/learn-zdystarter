package com.zdy.mystarter.spring.factory;

import org.springframework.lang.Nullable;

/**
 * TODO IOC容器学习·HierarchicalBeanFactory
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/22 17:34
 * @desc
 */
public interface ZDYHierarchicalBeanFactory extends ZDYBeanFactory{

    /**
     * 获取BeanFactory
     * @return
     */
    @Nullable
    ZDYBeanFactory getParentBeanFactory();

    /**
     * 判断是否包含本地Bean
     * @param var1
     * @return
     */
    boolean containsLocalBean(String var1);
}
