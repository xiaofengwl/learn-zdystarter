package com.zdy.eureka.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

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
@EnableDiscoveryClient      //Eureka客户端
@SpringBootApplication
public class ZdyEurekaClientServerApplication {

    /**
     * 启动Eureka服务
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ZdyEurekaClientServerApplication.class, args);
    }

}
