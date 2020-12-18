package com.zdy.mystarter.annotations;

import java.lang.annotation.*;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/17 10:14
 * @desc
 */
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CommonCheck {
    /**
     * 非必输项：检查描述
     * @return
     */
    String desc() default "";

    /**
     * 非必输项：检查类，比如：type={CheckAccount.class,CheckUserRight.class}
     */
    Class[] type() default{};

    /**
     * 检查执行器
     * @return
     */
    Checker[] checks() default {};
}
