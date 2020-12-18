package com.zdy.mystarter.sensitive.util;

import com.zdy.mystarter.sensitive.IStrategy;
import com.zdy.mystarter.sensitive.annotation.strategy.SensitiveStrategyTelephoneNumber;
import com.zdy.mystarter.sensitive.strategory.StrategyTelephoneNuber;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

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
public final class SensitiveStrategyBuiltInUtil {

    private static final Map<Class<?extends Annotation>,IStrategy> MAP=new HashMap<>();

    private SensitiveStrategyBuiltInUtil(){}

    public static IStrategy require(Class<?extends Annotation> annotationClass)throws Exception{
        IStrategy strategy=(IStrategy)MAP.get(annotationClass);
        if(null==strategy){
            throw new Exception("不支持的系统内置方法，用户请勿在自定义注解中使用[SensitiveStrategyBuiltIn]!");
        }else{
            return strategy;
        }
    }

    static {
        MAP.put(SensitiveStrategyTelephoneNumber.class,new StrategyTelephoneNuber());
    }



}
