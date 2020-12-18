package com.zdy.springcloud;

import com.zdy.springcloud.configuratioin.ZDYBeanConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

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
@EnableDiscoveryClient                  //启动consul连接
@Import({ZDYBeanConfig.class})          //导入配置类
@EnableFeignClients(basePackages = {	//启动Feign调用
        "com.zdy.springcloud.api"
})
@EnableCircuitBreaker                   //启动熔断机制
@ComponentScan("com.zdy.springcloud")
public class ZdySpringCloudServerApplication {

    /**
     * 启动Eureka服务
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ZdySpringCloudServerApplication.class, args);
    }

}
