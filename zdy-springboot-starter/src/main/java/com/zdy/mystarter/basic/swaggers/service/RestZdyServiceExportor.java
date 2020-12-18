package com.zdy.mystarter.basic.swaggers.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.Method;

/**
 * TODO 发布Rest接口服务
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/23 16:21
 * @desc
 */
public class RestZdyServiceExportor extends AbstractServiceExportor implements ApplicationContextAware{
    //日志
    private static Logger logger = LoggerFactory.getLogger(RestZdyServiceExportor.class);

    /**
     * 自定义的Rest请求映射处理器
     */
    ZDYRestHandlerMapping zdyRestHandlerMapping;

    /**
     * applicationContext对象
     */
    ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    public void setZdyRestHandlerMapping(ZDYRestHandlerMapping zdyRestHandlerMapping) {
        this.zdyRestHandlerMapping = zdyRestHandlerMapping;
    }

    /**
     * 重写抽象方法
     * @return
     */
    @Override
    String exportType() {
        // TODO Auto-generated method stub
        return "RESTful";
    }

    /**
     * todo 创建和注册服务Bean
     * @param serviceId     服务Id
     * @param i             序号
     * @return
     */
    protected  String createAndRegistZdyServiceBean(String serviceId, int i){
        logger.info("==[step-3]==========RestZdyServiceExportor.createAndRegistZdyServiceBean()======================");
        String action="";
        String beanName = "restZdyService";
        try{
            //参数准备
            String appName=applicationContext.getEnvironment().getProperty("zdy.application.name");
            //执行服务中的invoke方法
            Method method=RestZdyService.class.getDeclaredMethod("invoke", Object.class);
            Class<?> handlerType=RestZdyService.class;
            //开始注册
            action=zdyRestHandlerMapping.regist(appName,beanName,serviceId,method,handlerType);
        }catch (Exception e){
            e.printStackTrace();
        }
        logger.info("==[step-3.1]==========注册uri:"+action+"======================");

        return action;
    }

    /**
     * todo 以编码的方式为RestZdyService类创建Bean，注入IOC容器中,需要关注调用处的处理逻辑
     * @param serviceId
     * @param i
     */
    private void createBean(String serviceId, int i) {
        //获取应用对象
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
        // 获取bean工厂并转换为DefaultListableBeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();

        // 通过BeanDefinitionBuilder创建bean定义
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(RestZdyService.class);

        // 设置属性empBizExcutor,此属性引用已经定义的bean:zdyFlowExcutor,这里zdyFlowExcutor已经被spring容器管理了.
        beanDefinitionBuilder.addPropertyReference("zdyFlowExcutor", "zdyFlowExcutor");
        beanDefinitionBuilder.addPropertyValue("serviceId", serviceId);
        // beanDefinitionBuilder.addPropertyValue("flowId", flowId);
        beanDefinitionBuilder.setScope("singleton"); // 设置scope
        beanDefinitionBuilder.setLazyInit(false); // 设置是否懒加载
        // 注册bean
        String beanName = "DefaultZdyService" + i;
        //todo 向IOC容器中注入
        defaultListableBeanFactory.registerBeanDefinition(beanName, beanDefinitionBuilder.getRawBeanDefinition());

    }

}
