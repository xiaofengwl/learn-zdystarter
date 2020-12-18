package com.zdy.mystarter.annotations;

import java.lang.annotation.*;

/**
 * 自定义一个注解
 * 使用@ResponseBody当做元注解
 * Created by Jason on 2020/1/10.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@ResponseBody
public @interface ResultBeanResponseBody {
    /**
     * 因为@ResponseBody注解中什么都为写，所有当前这个继承注解，可以什么都不做处理
     * 就可以启动一样的作用
     */
}
