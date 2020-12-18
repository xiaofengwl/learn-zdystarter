package com.zdy.mystarter.basic.listeners;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;

/**
 * TODO 自定义启动监听器
 * <pre>
 *     当前这个类提供一个AOP契机点
 *     概念理解：
 *          ApplicationListener 是一个接口，里面只有一个onApplicationEvent方法。
 *          如果在上下文中部署一个实现了ApplicationListener接口的bean,
 *          那么每当在一个ApplicationEvent发布到 ApplicationContext时，
 *          也即调用ApplicationContext.publishEvent(事件event对象)方法，这个bean得到通知。
 *          类似于Oberver设计模式。
 *
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/19 11:10
 * @desc
 */
public class ZDYStartupListener implements ApplicationListener<ZDYStartupEvent>,ApplicationContextAware {

    /**
     * 内置一个上下文对象
     */
    private  ApplicationContext applicationContext=null;

    /**
     * 接收Spring应用上下文对象
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    /**
     * 处理监听事件
     * todo 需要在"ZDYStartupEvent"这个定制化的事件处理对象中定制处理方法
     * 理解：
     *     1.什么时候会执行该方法？
     *     回答：
     *          当ApplicationContext这个对象调用ApplicationContext.publishEvent(事件event对象)方法
     *          的时候，会进入该方法。
     *     2.这个方法的核心是ZDYStartupEvent 事件对象吗？
     *     回答：
     *          是的，这个对象是可以定制化处理的，需要在调用的时候指定，可以按照具体要求做定制化。
     *
     * @param zdyStartupEvent
     */
    @Override
    public void onApplicationEvent(ZDYStartupEvent zdyStartupEvent) {
        System.out.println("ZDYStartupListener__ZDYStartupEvent__开始事件监听处理...");
        zdyStartupEvent.zdyEventDealMethod();
    }
}
