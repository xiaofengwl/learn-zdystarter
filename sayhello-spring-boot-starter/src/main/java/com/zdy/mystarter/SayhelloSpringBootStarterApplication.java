package com.zdy.mystarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableScheduling						//打开定时任务注解
@EnableTransactionManagement			//启动事务管理器
@ComponentScan({"com.zdy.mystarter"})	//组件扫描
@EnableCircuitBreaker					//启动熔断机制
@EnableZuulProxy						//启动zuul网关
@EnableDiscoveryClient                  //启动consul连接
public class SayhelloSpringBootStarterApplication {

	/**
	 * 启动程序
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(SayhelloSpringBootStarterApplication.class, args);
	}

}
