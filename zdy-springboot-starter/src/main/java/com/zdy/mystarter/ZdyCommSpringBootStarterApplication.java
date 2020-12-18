package com.zdy.mystarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.EnableZuulServer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication

@EnableScheduling						//打开定时任务注解
@EnableTransactionManagement			//启动事务管理器
@ComponentScan({"com.zdy.mystarter"})	//组件扫描
@EnableCircuitBreaker                   //启动熔断机制
//@EnableZuulServer						//启动zuul-server网关
@EnableZuulProxy                        //启动zuul-proxy网关
@EnableDiscoveryClient                  //启动consul连接
@EnableFeignClients(basePackages = {	//启动Feign调用
		"com.zdy.mystarter.api"
})
//@EnableHystrix						//启动hystrix,内置了@EnableCircuitBreaker
@EnableCaching							//启动缓存机制
public class ZdyCommSpringBootStarterApplication {

	/**
	 * 启动程序
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(ZdyCommSpringBootStarterApplication.class, args);
	}

}
