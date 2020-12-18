package com.zdy.mystarter.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by Jason on 2020/1/7.
 * 在系统初始化完成之后执行以下逻辑
 * ApplicationRunner和CommandLineRunner 这2个接口都可支持
 */
@Order(4)
@Component
public class SpringFlowInitConfig implements CommandLineRunner{
    @Override
    public void run(String... strings) throws Exception {
        System.out.println("SpringFlowInitConfig...run...");
    }
}
