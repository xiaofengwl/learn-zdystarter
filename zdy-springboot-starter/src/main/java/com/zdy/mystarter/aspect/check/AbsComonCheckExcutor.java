package com.zdy.mystarter.aspect.check;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * TODO 添加主题
 * <pre>
 *     定制一个抽象类工具体的实现子类（检查类）继承，然后重写其中方法
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/17 10:26
 * @desc
 */
public abstract class AbsComonCheckExcutor implements CommonCheckExcutor {
    /**
     * 检查方法
     *
     * @param args 请求参数
     * @return true-检查通过，false-检查失败
     */
    @Override
    public boolean execute(Object[] args) {
        return false;
    }

    /**
     * 检查逻辑
     *
     * @param point 切入点对象
     * @param args  请求参数
     * @return true-检查通过，false-检查失败
     */
    @Override
    public boolean execute(ProceedingJoinPoint point, Object[] args) {
        return false;
    }
}
