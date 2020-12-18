package com.zdy.mystarter.basic.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.stereotype.Component;

/**
 * TODO 添加主题
 * <pre>
 *     实例化前／后，及框架设置Bean属性时调用该接口添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/10/9 15:52
 * @desc
 */
@Component
public class SpecilizationInstantiationAwareBeanPostProcessorAdapter extends InstantiationAwareBeanPostProcessorAdapter {

    Logger logger= LoggerFactory.getLogger(SpecilizationInstantiationAwareBeanPostProcessorAdapter.class);

    //在Bean对象实例化前调用
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        //仅对容器中的person bean处理
        if (beanName.contains("hello")) {
            logger.info("实例化前调用：InstantiationAwareBeanPostProcessorAdapter.postProcessBeforeInstantiation--->"+beanName);
        }
        return null;
    }

    //在Bean对象实例化后调用（如调用构造器之后调用）
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        //仅对容器中的person bean处理
        if (beanName.contains("hello")) {
            logger.info("实例化后调用：InstantiationAwareBeanPostProcessorAdapter.postProcessAfterInstantiation--->"+beanName+"=>"+bean);
        }
        return true;
    }


}
