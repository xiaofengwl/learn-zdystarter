package com.zdy.mystarter.aspect.check;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * TODO 添加主题
 * <pre>
 *    公共检查执行器接口
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/17 10:22
 * @desc
 */
public interface CommonCheckExcutor {

    /**
     * 检查方法
     * @param args 请求参数
     * @return  true-检查通过，false-检查失败
     */
    boolean execute(Object[] args);


    /**
     * 检查逻辑
     * @param point 切入点对象
     * @param args 请求参数
     * @return  true-检查通过，false-检查失败
     */
    boolean execute(ProceedingJoinPoint point,Object[] args);


}
