package com.zdy.mystarter.tools;

import com.zdy.mystarter.basic.awares.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * TODO BeanRegisterUtils工具类
 * <pre>
 *     动态注入Bean到IOC中
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/23 14:06
 * @desc
 */
public class BeanRegisterUtils {
    private static final Logger logger= LoggerFactory.getLogger(BeanRegisterUtils.class);

    /**
     * TODO 向Spring容器中注入bean(构造器注入)
     * @param beanName bean名称
     * @param beanClass bean Class对象
     * @param constructorArgs 参数
     * @param <T>
     */
    public static <T> void registerBean(String beanName, Class<T> beanClass, Object ... constructorArgs) {
        if (Objects.isNull(beanClass)) {
            if (logger.isDebugEnabled()) {
                logger.debug("beanClass为空，无法注入:", beanName);
            }
            return;
        }

        //todo 构建BeanDefinitionBuilder
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);

        //todo 添加Bean对象构造函数的参数
        if(!StringUtils.isEmpty(constructorArgs)){
            for(Object param:constructorArgs){
                builder.addConstructorArgValue(param);
            }
        }
       /* Optional.ofNullable(constructorArgs).ifPresent(argArr ->
                Arrays.stream(argArr).forEach(builder::addConstructorArgValue));*/

        //todo 从builder中获取到BeanDefinition对象
        BeanDefinition beanDefinition = builder.getRawBeanDefinition();

        //todo 获取spring容器中的IOC容器
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) SpringContextHolder.getApplicationContext().getAutowireCapableBeanFactory();

        //todo 向IOC容器中注入bean对象
        defaultListableBeanFactory.registerBeanDefinition(beanName, beanDefinition);
    }
}
