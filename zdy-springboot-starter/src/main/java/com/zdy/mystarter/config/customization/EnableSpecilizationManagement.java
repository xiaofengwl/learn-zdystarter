package com.zdy.mystarter.config.customization;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * TODO 定制一个启动注解:EnableXXXX注解，实现定制任务
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/9/1 12:02
 * @desc
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(EnableSpecilizationManageSelector.class)
public @interface EnableSpecilizationManagement {
}
