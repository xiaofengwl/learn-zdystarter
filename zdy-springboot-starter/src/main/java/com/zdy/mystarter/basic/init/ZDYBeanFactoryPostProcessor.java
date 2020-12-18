package com.zdy.mystarter.basic.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * TODO Spring中对IOC容器做处理的工厂
 * <pre>
 *     bean工厂的bean属性处理容器，说通俗一些就是可以管理我们的bean工厂内所有的beandefinition（未实例化）数据，
 *     可以随心所欲的修改属性。
 * </pre>
 * todo 重点说明:
 *    先执行这个BeanFactoryPostProcessor再循环每一个bean，然后执行BeanPostProcessor
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/21 11:26
 * @desc
 */
@Component
public class ZDYBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    Logger logger= LoggerFactory.getLogger(ZDYBeanFactoryPostProcessor.class);

    /**
     * todo 可以通过beanFactory可以获取bean的示例或定义等,同时可以修改bean的属性.
     *
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        logger.info("ZDYBeanFactoryPostProcessor...postProcessBeanFactory...");
        int count = beanFactory.getBeanDefinitionCount();
        String[] names = beanFactory.getBeanDefinitionNames();
        logger.info("当前BeanFactory中有"+count+" 个Bean");
        List restTemplates=new ArrayList();
        for (String name:names){
            if(name.toLowerCase().contains("resttemplate")){
                restTemplates.add(name);
            }
        }
        /**
         * TODO 可以过滤掉指定的Bean，或者主动添加一个Bean
         */
        Iterator<String> it=beanFactory.getBeanNamesIterator();
        while(it.hasNext()){
            if(it.next().equalsIgnoreCase("zdyInitalizer")){
                //针对某个bean做定制处理
                logger.info("--针对指定Bean在IOC实例化之前做定制处理--zdyInitalizer-----");
            }
        }
    }
}
