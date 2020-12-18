package com.zdy.mystarter.basic.init;

import com.zdy.mystarter.basic.proxy.ZDYGlobalInvkerCglib;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.stereotype.Component;

/**
 * TODO  后置处理器：初始化前后进行处理工作, 将后置处理器加入到容器中
 * <pre>
 * TODO BeanPostProcessor是Spring IOC容器给我们提供的一个扩展接口。
 * TODO 接口声明如下:
 *     public interface BeanPostProcessor {
 *        //bean初始化方法调用前被调用
 *        Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;
 *        //bean初始化方法调用后被调用
 *        Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
 *     }
 * TODO 运行顺序入下：
 *     ===Spring IOC容器实例化Bean===
 *     ===调用BeanPostProcessor的postProcessBeforeInitialization方法===
 *     ===调用bean实例的初始化方法===
 *     ===调用BeanPostProcessor的postProcessAfterInitialization方法===
 * TODO 使用场景：
 *     1.可以在IOC容器对所有Bean或者指定的Bean做处理的时候使用这种处理模式。
 *     2.
 *
 *
 *
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/21 11:18
 * @desc
 */
@Component
public class ZDYBeanPostProcessor implements BeanPostProcessor{

    Logger logger= LoggerFactory.getLogger(ZDYBeanPostProcessor.class);

    /**
     * IOC容器中所有的Bean都要执行-前置处理
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // TODO Auto-generated method stub
        if("zDYInitalizer".equalsIgnoreCase(beanName)){
            logger.info("postProcessBeforeInitialization..."+beanName+"=>"+bean);
        }
        //不代理直接返回
        if(!isProxy(bean)){
            return bean;
        }
        //可以被代理
        try{
            Enhancer enhancer=new Enhancer();
            enhancer.setSuperclass(bean.getClass());
            Callback callback=new ZDYGlobalInvkerCglib(bean);
            enhancer.setCallback(callback);
            return enhancer.create();
        }catch (Exception e){
            logger.error("代理类生成异常{},处理降级，该类不予代理。",beanName);
            return bean;
        }
    }

    /**
     * IOC容器中所有的Bean都要执行-后置处理
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // TODO Auto-generated method stub
        if("zDYInitalizer".equalsIgnoreCase(beanName)){
            logger.info("postProcessAfterInitialization..."+beanName+"=>"+bean);
        }
        return bean;
    }

    /**
     * 判断时候需要被代理
     * @param bean
     * @return
     */
    private boolean isProxy(Object bean){
        //不是指定包，不给代理
       try{
           //System.out.println("###############"+bean.getClass().getPackage().getName());
           if(!bean.getClass().getPackage().getName().startsWith("com.zdy.mystarter.controller")){
               return false;
           }
       }catch (Exception e){
           return false;
       }
       return true;
    }
}
