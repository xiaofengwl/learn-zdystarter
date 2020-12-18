package com.zdy.mystarter.annotations;

import java.lang.annotation.*;

/**
 * 自定义一个参数解析注解
 * Created by Jason on 2020/1/10.
 * 作用规则：
 *
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParameterAnntation {



    Class type();

}
