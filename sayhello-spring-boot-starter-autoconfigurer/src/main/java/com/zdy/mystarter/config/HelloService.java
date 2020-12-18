package com.zdy.mystarter.config;

/**
 * Created by Jason on 2020/1/7.
 */
public class HelloService {

    //读取配置类里面的配置信息
    HelloProperties helloProperties;

    public HelloProperties getHelloProperties(){
        return  helloProperties;
    }

    //赋值配置对象
    public void setHelloProperties(HelloProperties helloProperties) {
        this.helloProperties = helloProperties;
    }
    //简单业务逻辑
    public String sayHellZdy(String name){
        return helloProperties.getStart()+"-" +name + helloProperties.getEnd();
    }
}
