package com.zdy.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * TODO 服务注册中心
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/25 17:29
 * @desc
 */
@EnableEurekaServer                 //启动Eureka服务
@SpringBootApplication
public class EurekaServerApplication {

    /**
     * 启动Eureka服务
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }

}
