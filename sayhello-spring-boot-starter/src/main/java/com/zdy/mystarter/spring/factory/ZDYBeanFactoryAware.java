package com.zdy.mystarter.spring.factory;

import org.springframework.beans.BeansException;

/**
 * TODO IOC容器学习·自定义一个BeanFacotoryAware
 * <pre>
 *     为BeanFactory定制一个获取工厂对象的接口
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/22 13:53
 * @desc
 */
public interface ZDYBeanFactoryAware extends ZDYAware {

    /**
     * 定制获取方法
     * @param var1
     * @throws BeansException
     */
    void setBeanFactory(ZDYBeanFactory var1) throws BeansException;

}
