package com.zdy.mystarter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Jason on 2020/1/7.
 */
//声明这是一个配置类
@Configuration
//应用启动类的项目是web项目吗，此自动配置模块才会生效
@ConditionalOnWebApplication
//加载配置Bean对象到容器中
@EnableConfigurationProperties(HelloProperties.class)
public class HelloServiceAutoConfiguration {

    @Autowired
    HelloProperties helloProperties;

    @Bean//方法返回结果对象加载到容器
    public HelloService helloService(){
        //新建业务逻辑处理对象，并返回加载到容器中，
        // 这样引用启动器的项目就可以 @Autowired  HelloService 对象直接使用了
        HelloService helloService = new HelloService();
        helloService.setHelloProperties(helloProperties);
        return helloService;
    }

}
