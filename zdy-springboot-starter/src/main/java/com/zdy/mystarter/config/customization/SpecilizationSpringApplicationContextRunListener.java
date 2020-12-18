package com.zdy.mystarter.config.customization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * TODO 定制一个SpringApplicationRunListener
 * todo
 * <pre>
 *    SpringApplicationRunListener 接口的作用主要就是在Spring Boot 启动初始化的过程中可以通过SpringApplicationRunListener接口
 *    回调来让用户在启动的各个流程中可以加入自己的逻辑。
 *    Spring Boot启动过程的关键事件（按照触发顺序）包括：
 *
 *       开始启动
 *       Environment构建完成
 *       ApplicationContext构建完成
 *       ApplicationContext完成加载
 *       ApplicationContext完成刷新并启动
 *       启动完成
 *       启动失败
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/9/2 14:39
 * @desc
 */
public class SpecilizationSpringApplicationContextRunListener implements SpringApplicationRunListener {

    private static Logger logger= LoggerFactory.getLogger(SpecilizationSpringApplicationContextRunListener.class);

    // 在run()方法开始执行时，该方法就立即被调用，可用于在初始化最早期时做一些工作
    public void starting(){
        logger.info("SpecilizationSpringApplicationContextRunListener-starting");
    }
    // 当environment构建完成，ApplicationContext创建之前，该方法被调用
    public void environmentPrepared(ConfigurableEnvironment environment){
        logger.info("SpecilizationSpringApplicationContextRunListener-environmentPrepared");
    }
    // 当ApplicationContext构建完成时，该方法被调用
    public void contextPrepared(ConfigurableApplicationContext context){
        logger.info("SpecilizationSpringApplicationContextRunListener-contextPrepared");
    }
    // 在ApplicationContext完成加载，但没有被刷新前，该方法被调用
    public void contextLoaded(ConfigurableApplicationContext context){
        logger.info("SpecilizationSpringApplicationContextRunListener-contextLoaded");
    }
    // 在ApplicationContext刷新并启动后，CommandLineRunners和ApplicationRunner未被调用前，该方法被调用
    public void started(ConfigurableApplicationContext context){
        logger.info("SpecilizationSpringApplicationContextRunListener-started");
    }
    // 在run()方法执行完成前该方法被调用
    public void running(ConfigurableApplicationContext context){
        logger.info("SpecilizationSpringApplicationContextRunListener-running");
    }
    // 当应用运行出错时该方法被调用
    public void failed(ConfigurableApplicationContext context, Throwable exception){
        logger.info("SpecilizationSpringApplicationContextRunListener-failed");
    }

}
