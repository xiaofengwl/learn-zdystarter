package com.zdy.mystarter.spring.core;

/**
 * TODO IOC容器学习·AttributeAccessor
 * <pre>
 *     定义了一些关于属性的操作方法
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/22 14:15
 * @desc
 */
public interface ZDYAttributeAccessor {

    /**
     * 设置属性
     * @param var1
     * @param var2
     */
    void setAttribute(String var1, Object var2);

    /**
     * 获取属性
     * @param var1
     * @return
     */
    Object getAttribute(String var1);

    /**
     * 移除属性
     * @param var1
     * @return
     */
    Object removeAttribute(String var1);

    /**
     * 判断是否有某种属性
     * @param var1
     * @return
     */
    boolean hasAttribute(String var1);

    /**
     * 获取属性的名称
     *
     * @return
     */
    String[] attributeNames();

}
