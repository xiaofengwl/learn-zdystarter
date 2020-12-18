package com.zdy.mystarter.annotations;

import java.lang.annotation.*;

/**
 * TODO 日志打印
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/21 9:50
 * @desc
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface WebLog {

    /**
     * 日志描述信息
     * @return
     */
    String description() default "";
}
