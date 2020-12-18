package com.zdy.mystarter.basic.awares;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * TODO Aware接口测试-1
 * <pre>
 *     Aware.java是个没有定义任何方法的接口，拥有众多子接口，
 *     在spring源码中有多处都在使用这些子接口完成各种场景下的回调操作，
 *     当业务有需要时，我们只需创建类来实现相关接口，再声明为bean，就可以被spring容器主动回调；
 * </pre>
 *  AOP思想
 * @author lvjun
 * @version 1.0
 * @date 2020/3/17 9:45
 * @desc
 */
@Component
public class SpringContextHolder implements ApplicationContextAware{

    /**
     * 内置一个上下文对象
     */
    private static ApplicationContext applicationContext=null;

    /**
     * 接收Spring应用上下文对象
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext=applicationContext;

    }

    /**
     * 提供获取应用上下文对象方法
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取Bean-1
     * @param requireType
     * @param <T>
     * @return  泛型
     */
    public static <T> T getBean(Class<T> requireType){
        return applicationContext.getBean(requireType);
    }

   /* *//**
     * 获取Bean-2
     * @param beanName
     * @return Object
     *//*
    public static Object getBean(String beanName){
        return applicationContext.getBean(beanName);
    }*/

    /**
     * 获取Bean-2
     * @param beanName
     * @return Object
     */
    public static <T>  T getBean(String beanName){
        return (T)applicationContext.getBean(beanName);
    }

    /**
     * 提供一个方法向Spring容器中注入Bean
     * @param  tClass Class<?> 要注入的类型
     */
    public static void autoWireBean(Class<?> tClass){
        //todo 自动装配将对象以Bean的形式注入到Spring容器中
        AutowireCapableBeanFactory autowireCapableBeanFactory=applicationContext.getAutowireCapableBeanFactory();

    }

    /**
     * 提供一个方法向Spring容器中注入Bean
     * @param  object 注入的类实例
     */
    public static void autoWireBeanByProperty(Object object){
        //todo 自动装配将对象以Bean的形式注入到Spring容器中
        AutowireCapableBeanFactory autowireCapableBeanFactory=applicationContext.getAutowireCapableBeanFactory();

    }

}
