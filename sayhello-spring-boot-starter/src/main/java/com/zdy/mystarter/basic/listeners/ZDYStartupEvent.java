package com.zdy.mystarter.basic.listeners;

import org.springframework.context.ApplicationEvent;

/**
 * TODO 定制启动事件对象
 * <pre>
 *     支持定制化处理逻辑
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/19 11:13
 * @desc
 */
public class ZDYStartupEvent extends ApplicationEvent {

    /**
     * 特别说明：
     *
     * @param source  这是在使用ApplicationContext对象的时候可以指定一个事件对象
     */
    public ZDYStartupEvent(Object source) {
        super(source);
        System.out.println("ZDYStartupEvent_zdyEventDealMethod_事件对象被创建...");
    }

    /**
     * 定制化一个事件对象中的处理方法，在外部被调用
     * @param props
     */
    public void zdyEventDealMethod(Object...props){
        System.out.println("ZDYStartupEvent_zdyEventDealMethod_事件定制化处理逻辑1...");
    }
}
