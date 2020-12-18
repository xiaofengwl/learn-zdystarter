package com.zdy.mystarter.sensitive.annotation.strategy;

import com.zdy.mystarter.sensitive.annotation.metadata.SensitiveStrategy;
import com.zdy.mystarter.sensitive.strategory.SensitiveStrategyBuiltIn;

import java.lang.annotation.*;

/**
 * TODO 电话号码脱敏
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/21 14:19
 * @desc
 */
@Inherited
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@SensitiveStrategy(SensitiveStrategyBuiltIn.class)
public @interface SensitiveStrategyTelephoneNumber {
}
