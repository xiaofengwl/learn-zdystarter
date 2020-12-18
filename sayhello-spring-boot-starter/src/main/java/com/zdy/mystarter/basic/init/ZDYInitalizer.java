package com.zdy.mystarter.basic.init;

import com.zdy.mystarter.basic.awares.SpringContextHolder;
import com.zdy.mystarter.basic.listeners.ZDYStartupEvent;
import com.zdy.mystarter.tools.BeanRegisterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * TODO 随便指定一个了做Bean的初始化测试
 * <pre>
 *     主要测试InitializingBean接口，顺带测试接口ApplicationContextAware
 *     todo 发现现象：
 *        ApplicationContextAware.setApplicationContext()
 *        优先执行于
 *        InitializingBean.afterPropertiesSet()
 *     todo 总结：
 *          此类的装配方式适用于自定义一些模块的初始化阶段。
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/19 9:25
 * @desc
 */
public class ZDYInitalizer implements InitializingBean,ApplicationContextAware {

    private static final Logger logger= LoggerFactory.getLogger(ZDYInitalizer.class);
    /**
     * 内置一个上下文对象
     */
    private  ApplicationContext applicationContext=null;
    /**
     * 准备接收传入的资源文件对象
     */
    private ZDYProperties zdyProperties;
    /**
     * 构造器，带有资源注入的构造器
     */
    public ZDYInitalizer(){}
    public ZDYInitalizer(ZDYProperties zdyProperties){
        System.out.println("ZDYinitalizer__InitializingBean_资源注入...");
        this.zdyProperties=zdyProperties;
    }

    /**
     * 凡是实现了接口InitializingBean的类，在初始化bean的时候都会执行该方法。
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("ZDYinitalizer__InitializingBean_准备初始化...");
        zdyInit();
        System.out.println("ZDYinitalizer__InitializingBean_初始化完毕...");
    }

    /**
     * 接收Spring应用上下文对象
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
        System.out.println("ZDYinitalizer__ApplicationContextAware_数据装载："+ this.applicationContext);
    }

    /**
     * 某种结构的初始化操作
     */
    public void zdyInit(){
        System.out.println("ZDYinitalizer__zdyInit()");

        //todo 在这里使用ApplicationContext的事件监听机制
        //创建一个事件对象
        ZDYStartupEvent zdyStartupEvent=new ZDYStartupEvent(new Object[]{});//传入资源对象
        //发布事件
        System.out.println("ZDYInitalizer__zdyInit_准备发布事件...");
        this.applicationContext.publishEvent(zdyStartupEvent);
        System.out.println("ZDYInitalizer__zdyInit_事件已经发布...");

        /**
         *  todo 不依赖Spring容器的获取Bean方式
         *  todo 应用场景：
         *      .......
         */
        //todo 获取自动装配工厂实例
        ZDYDataVo zdyDataVo=new ZDYDataVo();
        AutowireCapableBeanFactory autowireCapableBeanFactory=SpringContextHolder.getApplicationContext().getAutowireCapableBeanFactory();
        //todo 已存在实例-生成一个装填了属性数据的代理对象
        autowireCapableBeanFactory.autowireBeanProperties(zdyDataVo,AutowireCapableBeanFactory.AUTOWIRE_BY_NAME,false);
        //todo 直接Class-生成一个装填了属性数据的代理对象
        ZDYDataVo bean= (ZDYDataVo)autowireCapableBeanFactory.createBean(ZDYDataVo.class,AutowireCapableBeanFactory.AUTOWIRE_BY_NAME,false);
        /**
         * todo 费解：如果autowireCapableBeanFactory主要的autowireBean(Object existenBean)
         */
        ZDYDataVo zdyDataVo2=new ZDYDataVo();
        autowireCapableBeanFactory.autowireBean(zdyDataVo2);

        /**
         * todo 向IOC容器中动态注入Bean
         * todo 应用场景：
         *    可以手动扫面指定的外部类或者将其他非Spring的框架内容，经过转化处理生成一个实例对象
         *    然后动态注入到IOC容器中。
         *    然后通过ApplicationContext.getBean("beanName")或者ApplicationContext.getBean(Class<?> beanClass)的方式来获取。
         *
         */
        BeanRegisterUtils.registerBean("zDYDataVo",ZDYDataVo.class);

    }

}
