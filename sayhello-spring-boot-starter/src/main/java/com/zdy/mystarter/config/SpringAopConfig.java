package com.zdy.mystarter.config;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by Jason on 2020/1/7.
 * @Order注解用来控制注解类的加载顺序
 * Order是顺序，此注解可操作于类、方法、字段，当作用在类时，值越小，则加载的优先级越高！
 * 测试不生效，目前按照类在包中的顺序加载去执行
 */
@Order(0)
@Component
//@Aspect       //切面注解需要添加依赖
public class SpringAopConfig {



}
