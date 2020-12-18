package com.zdy.mystarter.config;

import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

/**
 * TODO 在Spring容器生命周期间做一些处理
 * <pre>
 * @date 2020/3/17 8:55
 * @desc
 *     如果业务上需要在spring容器启动和关闭的时候做一些操作，可以自定义SmartLifecycle接口的实现类来扩展
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 */
//TODO 一定要组件化
@Component
public class SpringIocSmartLifecycle implements SmartLifecycle{

    //todo 设置是否自动启动标记
    private boolean isRunning=false;
    /**
     * 【1】bean初始化完毕后，该方法会被执行
     */
    @Override
    public void start() {
        isRunning=true;
        System.out.println("SpringIocSmartLifecycle_start()-isRunning:"+isRunning);
    }

    /**
     * 【2】容器关闭后：
     *  spring容器发现当前对象实现了SmartLifecycle，就调用stop(Runnable)，
     *  如果只是实现了Lifecycle，就调用stop()
     */
    @Override
    public void stop() {
        isRunning=false;
        System.out.println("SpringIocSmartLifecycle_stop()-isRunning:"+isRunning);
    }

    /**
     * 【3】当前状态
     * @return
     */
    @Override
    public boolean isRunning() {
        System.out.println("SpringIocSmartLifecycle_isRunning()-isRunning:"+isRunning);
        return isRunning;
    }

    /**
     * 【4】返回值决定start方法在众多Lifecycle实现类中的执行顺序(stop也是)
     * 执行顺序设置
     * 可以自定义优先级（getPhase）
     * @return
     */
    @Override
    public int getPhase() {
        System.out.println("SpringIocSmartLifecycle_getPhase():0-isRunning:"+isRunning);
        //todo 设置在众多LifeCycle中第一个执行
        return 0;
    }

    /**
     * 【5】start方法被执行前先看此方法返回值，返回false就不执行start方法了
     * 说明：只有这个方法返回true,才会执行start()
     * @return
     */
    @Override
    public boolean isAutoStartup() {
        System.out.println("SpringIocSmartLifecycle_isAutoStartup():-isRunning:"+isRunning+"[-->返回true]");
        return true;
    }

    /**
     * 【6】容器关闭后：
     spring容器发现当前对象实现了SmartLifecycle，就调用stop(Runnable)，
     如果只是实现了Lifecycle，就调用stop()
     * @param callback
     */
    @Override
    public void stop(Runnable callback) {
        System.out.println("SpringIocSmartLifecycle_stop(Runnable callback)-isRunning:"+isRunning);;
        isRunning=false;
    }


}
