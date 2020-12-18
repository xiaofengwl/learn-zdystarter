package com.zdy.mystarter.config.customization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * TODO 添加主题
 * <pre>
 *    用于在spring容器刷新之前初始化Spring ConfigurableApplicationContext的回调接口。（剪短说就是在容器刷新之前调用该类的 initialize 方法。并将 ConfigurableApplicationContext 类的实例传递给该方法）
 *    通常用于需要对应用程序上下文进行编程初始化的web应用程序中。例如，根据上下文环境注册属性源或激活配置文件等。
 *    可排序的（实现Ordered接口，或者添加@Order注解）
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/9/2 14:16
 * @desc
 */
public class SpecilizationApplicationContextInitializer implements ApplicationContextInitializer{

    private static Logger logger= LoggerFactory.getLogger(SpecilizationApplicationContextInitializer.class);
    /**
     * Initialize the given application context.
     *
     * @param applicationContext the application to configure
     */
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        logger.info("SpecilizationApplicationContextInitializer-initialize");
    }
}
