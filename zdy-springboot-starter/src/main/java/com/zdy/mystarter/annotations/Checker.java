package com.zdy.mystarter.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO checker
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/9/1 9:53
 * @desc
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface Checker {

    /**
     * 非必输项：检查描述
     * @return
     */
    String desc() default "";

    /**
     * 必输项-检查类，比如type={AAA.class,BBB.class}
     * @return
     */
    Class[] type();

    /**
     * 检查方法
     * @return
     */
    String[] method() default{};

}
