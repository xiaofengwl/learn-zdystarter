package com.zdy.mystarter.spring.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * TODO IOC容器学习·ListableBeanFactory
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/22 17:24
 * @desc
 */
public interface ZDYListableBeanFactory  extends ZDYBeanFactory {

    /**
     * 判断是否包含指定的Bean
     * @param var1
     * @return
     */
    boolean containsBeanDefinition(String var1);

    /**
     * 获取Bean的数量
     * @return
     */
    int getBeanDefinitionCount();

    /**
     * 获取所有Bean的name
     * @return
     */
    String[] getBeanDefinitionNames();

    /**
     * 根据类型获取Bean的names-4件套
     * @param var1
     * @return
     */
    String[] getBeanNamesForType(ResolvableType var1);

    String[] getBeanNamesForType(ResolvableType var1, boolean var2, boolean var3);

    String[] getBeanNamesForType(@Nullable Class<?> var1);

    String[] getBeanNamesForType(@Nullable Class<?> var1, boolean var2, boolean var3);

    /**
     * 根据Class类型获取指定的Bean，返回Map
     * @param var1
     * @param <T>
     * @return
     * @throws BeansException
     */
    <T> Map<String, T> getBeansOfType(@Nullable Class<T> var1) throws BeansException;

    <T> Map<String, T> getBeansOfType(@Nullable Class<T> var1, boolean var2, boolean var3) throws BeansException;

    /**
     * 根据注解类型来获取Bean的names数组
     * @param var1
     * @return
     */
    String[] getBeanNamesForAnnotation(Class<? extends Annotation> var1);

    /**
     * 根据注解类型来获取Bean的Map
     * @param var1
     * @return
     * @throws BeansException
     */
    Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> var1) throws BeansException;

    /**
     * 在Bean上找注解
     * @param var1
     * @param var2
     * @param <A>
     * @return
     * @throws NoSuchBeanDefinitionException
     */
    @Nullable
    <A extends Annotation> A findAnnotationOnBean(String var1, Class<A> var2) throws NoSuchBeanDefinitionException;
}
