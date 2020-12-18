package com.zdy.mystarter.spring.core;

/**
 * TODO IOC容器学习·AliasRegistry
 * <pre>
 *     AliasRegistry·别名注册接口
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/22 17:03
 * @desc
 */
public interface ZDYAliasRegistry {
    /**
     * 注册别名
     * @param var1
     * @param var2
     */
    void registerAlias(String var1, String var2);

    /**
     * 移除别名
     * @param var1
     */
    void removeAlias(String var1);

    /**
     * 判断是否是别名
     * @param var1
     * @return
     */
    boolean isAlias(String var1);

    /**
     * 获取所有别名，返回String[]
     * @param var1
     * @return
     */
    String[] getAliases(String var1);
}
