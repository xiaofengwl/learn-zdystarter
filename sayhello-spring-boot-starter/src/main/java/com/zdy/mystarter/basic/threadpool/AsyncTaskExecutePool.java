package com.zdy.mystarter.basic.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义一个线程池配置类--->异步执行线程池
 * Created by Jason on 2020/1/10.
 */
@Configuration
public class AsyncTaskExecutePool implements AsyncConfigurer {
    /**
     * 日志
     */
    Logger logger= LoggerFactory.getLogger(AsyncTaskExecutePool.class);

    /**
     * 注入线程池配置
     */
    @Autowired
    private TaskThreadPoolConfig config;

    /**
     * 重写线程池配置
     * @return
     */
    @Override
    public Executor getAsyncExecutor() {
        logger.info("begin to iniit AsyncExecutor...");
        ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(config.getCorePoolSize());
        executor.setMaxPoolSize(config.getMaxPoolSize());
        executor.setQueueCapacity(config.getQueueCapacity());
        executor.setKeepAliveSeconds(config.getKeepAliveSeconds());
        executor.setThreadNamePrefix(config.getThreadNamePrefix());

        /**
         * 线程池配置的拒绝策略
         * （1）AbortPolicy：直接抛出java.util.concurrent.RejectedExecutionException
         * （2）CallerRunsPolicy策略：主线程直接执行该任务，执行完成之后尝试添加下一个任务到线程池中，
         *  这样可以有效降低向线程池内添加任务的速度。
         *
         *  建议使用：CallerRunsPolicy策略，任务不会丢失
         */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        logger.info("end to iniit AsyncExecutor...");
        return executor;
    }

    /**
     * 异常处理器
     * @return
     */
    @Nullable
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncUncaughtExceptionHandler() {
            @Override
            public void handleUncaughtException(Throwable ex, Method method, Object... params) {
                logger.error("异常信息："+ex.getMessage(),ex);
                logger.error("exception method："+method.getName());
            }
        };
    }
}
