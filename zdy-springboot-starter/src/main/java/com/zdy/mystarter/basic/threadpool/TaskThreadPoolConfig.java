package com.zdy.mystarter.basic.threadpool;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * TODO 线程池配置类
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/25 16:26
 * @desc
 */
@Configuration
@ConfigurationProperties(prefix = "spring.task.pool")
@Data
public class TaskThreadPoolConfig {

    /**
     * 核心线程数
     */
    private int corePoolSize=5;

    /**
     * 最大线程数
     */
    private int maxPoolSize=50;

    /**
     * 线程池维护线程允许的空闲时间
     */
    private int keepAliveSeconds=60;

    /**
     * 队列长度
     */
    private int queueCapacity=10000;

    /**
     * 线程名称前缀
     */
    private String threadNamePrefix="ZDY-AsyncTask-";



}
