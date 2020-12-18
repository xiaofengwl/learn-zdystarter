package com.zdy.mystarter.sensitive.annotation.metadata;

import com.zdy.mystarter.sensitive.IStrategy;

import java.lang.annotation.*;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/21 13:59
 * @desc
 */
@Inherited
@Documented
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SensitiveStrategy {
    /**
     *
     * @return
     */
    Class<? extends IStrategy> value();
}
