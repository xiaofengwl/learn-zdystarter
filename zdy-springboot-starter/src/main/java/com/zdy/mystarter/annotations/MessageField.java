package com.zdy.mystarter.annotations;

import java.lang.annotation.*;

/**
 * TODO 报文字段
 * <pre>
 *     主要为了做报文内容和实体属性的数据映射关系
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/1/17 14:19
 * @desc
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MessageField {

    /**
     * json类型数据映射
     * @return
     */
    String jsonField() default "";

    /**
     * xml类型数据映射
     * @return
     */
    String xmlField() default "";

    /**
     * 是否是引用类型,默认false-非引用类型，true-是引用类型
     * @return
     */
    boolean isReference() default false;

    /**
     * 是否是List
     * @return
     */
    boolean isCollection() default false;
    /**
     * 和isCollection配套使用，默认false
     * true-List参数为引用类型，false-List参数为普通类型
     * @return
     */
    boolean isObjCollection() default false;
    /**
     * 和isCollection配套使用，
     * true-List参数为引用类型,此时该引用的类型
     * @return
     */
    Class type() default Class.class;

}
