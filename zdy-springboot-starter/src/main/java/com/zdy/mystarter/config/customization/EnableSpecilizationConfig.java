package com.zdy.mystarter.config.customization;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * TODO  定制一个自动装配启动类
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/9/1 12:09
 * @desc
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(SpecilizationRegistrar.class)
public @interface EnableSpecilizationConfig {
    String[] value() default {};

    int order() default 0;
}
