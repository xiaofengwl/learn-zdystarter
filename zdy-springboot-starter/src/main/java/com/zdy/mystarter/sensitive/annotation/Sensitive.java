package com.zdy.mystarter.sensitive.annotation;

import com.zdy.mystarter.sensitive.ICondition;
import com.zdy.mystarter.sensitive.IStrategy;
import com.zdy.mystarter.sensitive.condition.ConditionAlwaysTrue;

import java.lang.annotation.*;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/21 13:55
 * @desc
 */
@Inherited
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Sensitive {
    /**
     *
     * @return
     */
    Class<? extends ICondition> condition() default ConditionAlwaysTrue.class;

    /**
     *
     * @return
     */
    Class<? extends IStrategy> strategy();



}
