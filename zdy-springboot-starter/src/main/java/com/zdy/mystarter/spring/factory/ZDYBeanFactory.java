package com.zdy.mystarter.spring.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;

/**
 * TODO IOC容器学习·自定义一个BeansFacotory
 * <pre>
 *     模拟着Spring的BeansFactory学习
 *  理解：
 *       BeanFactory是IOC容器的最底层的接口，为IOC容器指定一些基本的功能规范。
 *   其他一些花里胡哨的IOC子产品容器都必须实现这个BeanFactory基础接口。
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/22 13:35
 * @desc
 */
public interface ZDYBeanFactory {

    /**
     * 工厂Bean的前缀&
     */
    String FACTORY_BEAN_PREFIX = "&";

    /**
     * 提供获取容器中对象的5种方式
     * @param var1
     * @return
     * @throws BeansException
     */
    Object getBean(String var1) throws BeansException;
    <T> T getBean(String var1, Class<T> var2) throws BeansException;
    Object getBean(String var1, Object... var2) throws BeansException;
    <T> T getBean(Class<T> var1) throws BeansException;
    <T> T getBean(Class<T> var1, Object... var2) throws BeansException;

    /**
     * 提供获取Bean的提供者的2种方式
     * @param var1
     * @param <T>
     * @return
     */
    <T> ObjectProvider<T> getBeanProvider(Class<T> var1);
    <T> ObjectProvider<T> getBeanProvider(ResolvableType var1);

    /**
     * 检查是否含有指定的Bean
     * @param var1
     * @return
     */
    boolean containsBean(String var1);

    /**
     * 检查Bean是否是单例Bean
     * @param var1
     * @return
     * @throws NoSuchBeanDefinitionException
     */
    boolean isSingleton(String var1) throws NoSuchBeanDefinitionException;

    /**
     * 检查Bean是否是prototype类型
     * @param var1
     * @return
     * @throws NoSuchBeanDefinitionException
     */
    boolean isPrototype(String var1) throws NoSuchBeanDefinitionException;

    /**
     * 判断Bean是否是指定的类型-2种方式
     * @param var1
     * @param var2
     * @return
     * @throws NoSuchBeanDefinitionException
     */
    boolean isTypeMatch(String var1, ResolvableType var2) throws NoSuchBeanDefinitionException;
    boolean isTypeMatch(String var1, Class<?> var2) throws NoSuchBeanDefinitionException;

    /**
     * 获取Bean的类型-2种方式
     * @Nullable 允许方法返回null
     * @param var1
     * @return
     * @throws NoSuchBeanDefinitionException
     */
    @Nullable
    Class<?> getType(String var1) throws NoSuchBeanDefinitionException;
    @Nullable
    Class<?> getType(String var1, boolean var2) throws NoSuchBeanDefinitionException;

    /**
     * 获取Bean的别名
     * @param var1
     * @return
     */
    String[] getAliases(String var1);


}
