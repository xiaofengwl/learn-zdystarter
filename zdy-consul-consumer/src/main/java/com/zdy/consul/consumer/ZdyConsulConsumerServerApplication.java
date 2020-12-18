package com.zdy.consul.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * TODO
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/25 17:29
 * @desc
 */
@SpringBootApplication
@ComponentScan({"com.zdy.consul.consumer"})
public class ZdyConsulConsumerServerApplication {

    /**
     * 启动Eureka服务
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ZdyConsulConsumerServerApplication.class, args);
    }

}
