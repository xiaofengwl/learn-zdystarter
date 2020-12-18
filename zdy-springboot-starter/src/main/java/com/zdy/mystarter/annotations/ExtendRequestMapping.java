package com.zdy.mystarter.annotations;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.*;

/**
 * 自定义注解@ExtendRequestMapping，实现@RequestMapping的url配置
 * 使用原有的注解作为元注解，配合@AliasFor将当前注解的功能指向为元注解的属性上，即可不用做功能实现
 * Created by Jason on 2020/1/8.
 *
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping
public @interface ExtendRequestMapping {
    /**
     * 对接@RequestMapping中的value
     * @return
     */
    @AliasFor(value = "value",annotation = RequestMapping.class)
    String[] value() default {};



}
