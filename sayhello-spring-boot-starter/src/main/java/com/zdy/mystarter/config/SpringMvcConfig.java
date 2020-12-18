package com.zdy.mystarter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 以编码的方式做配置
 * Created by Jason on 2020/1/7.
 * @Order注解用来控制注解类的加载顺序
 * Order是顺序，此注解可操作于类、方法、字段，当作用在类时，值越小，则加载的优先级越高！
 * 测试不生效，目前按照类在包中的顺序加载去执行
 */
@Configuration
public class SpringMvcConfig {

    /**
     * 补充：此处可以重新定义一个Property对象，然后覆盖启动器中的默认设置，比如：reWriteVoProperties
     */
    @Autowired
    HelloProperties helloProperties;
    @Resource
    HelloProperties reWriteVoProperties;
    /**
     * 覆盖自定义启动器中的注入IOC中的Bean
     * @return
     */ 
    @Bean
    @ConditionalOnMissingBean({HelloService.class})
    public HelloService getHelloService(){
        System.out.println("SpringMvcConfig...getHelloService1....");
        HelloService helloService = new HelloService();
        helloProperties.setStart("updated_start");
        helloProperties.setEnd("updated_end");
        helloService.setHelloProperties(helloProperties);
        return helloService;
    }

    /**
     * 重复覆盖
     * @return
     * 总结:
     *   在分析了依赖的启动器的指定配置的处理逻辑，我们就可以采用覆盖的形式去修改启动器的默认配合
     */
    @Bean
    //@ConditionalOnBean({HelloService.class})
    public HelloService getHelloService2(){
        System.out.println("SpringMvcConfig...getHelloService2....");
        HelloService helloService = new HelloService();
        helloProperties.setStart(reWriteVoProperties.getStart());
        helloProperties.setEnd(reWriteVoProperties.getEnd());
        helloService.setHelloProperties(helloProperties);
        return helloService;
    }


}
