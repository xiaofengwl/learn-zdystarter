package com.zdy.mystarter.basic.awares;

import org.springframework.beans.factory.BeanNameAware;

/**
 * TODO 添加主题
 * <pre>
 *    实现BeanNameAware接口，方法setBeanName被调用的时候会打印当前堆栈信息：
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/17 9:53
 * @desc
 */
public class CustomizeBeanNameAware implements BeanNameAware{
    private String beanName;
    /**
     * 设置Bean的name
     * @param s
     */
    @Override
    public void setBeanName(String s) {
        System.out.println("CustomizeBeanNameAware_setBeanName()");
        this.beanName = beanName;
    }
    public String getBeanName() {
        System.out.println("CustomizeBeanNameAware_getBeanName()");
        return this.beanName;
    }
}
