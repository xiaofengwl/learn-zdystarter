package com.zdy.mystarter.annotations;

import java.lang.annotation.*;

/**
 * 此注解暂未做功能实现的处理，仅仅为了测试AOP切面注解@Aspect
 * Created by Jason on 2020/1/7.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HelloAnnotation {
    String value() default "";
}

