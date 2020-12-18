package com.zdy.mystarter.sensitive.annotation.metadata;

import com.zdy.mystarter.sensitive.ICondition;

import java.lang.annotation.*;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/21 13:58
 * @desc
 */
@Inherited
@Documented
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SensitiveCondition {
    /**
     *
     * @return
     */
    Class<? extends ICondition> value();
}
