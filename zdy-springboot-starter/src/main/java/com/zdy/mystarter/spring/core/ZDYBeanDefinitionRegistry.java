package com.zdy.mystarter.spring.core;

import com.zdy.mystarter.spring.config.ZDYBeanDefinition;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

/**
 * TODO IOC容器学习·BeanDefinitionRegistry
 * <pre>
 *     BeanDefinitionRegistry接口完成将已经在载入的Bean注册到IOC容器中的工作。
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/22 17:05
 * @desc
 */
public interface ZDYBeanDefinitionRegistry extends ZDYAliasRegistry{

    /**
     * 将Bean注册到IOC容器中
     * @param var1
     * @param var2
     * @throws BeanDefinitionStoreException
     */
    void registerBeanDefinition(String var1, ZDYBeanDefinition var2) throws BeanDefinitionStoreException;

    /**
     * 移除指定的Bean
     * @param var1
     * @throws NoSuchBeanDefinitionException
     */
    void removeBeanDefinition(String var1) throws NoSuchBeanDefinitionException;

    /**
     * 获取Bean
     * @param var1
     * @return
     * @throws NoSuchBeanDefinitionException
     */
    ZDYBeanDefinition getBeanDefinition(String var1) throws NoSuchBeanDefinitionException;

    /**
     * 判断是否包含指定的Bean
     * @param var1
     * @return
     */
    boolean containsBeanDefinition(String var1);

    /**
     * 获取所有的Bean的name
     * @return
     */
    String[] getBeanDefinitionNames();

    /**
     * 获取所有的bean的数量
     * @return
     */
    int getBeanDefinitionCount();

    /**
     * 判断当前Bean是否在被使用
     * @param var1
     * @return
     */
    boolean isBeanNameInUse(String var1);
}
