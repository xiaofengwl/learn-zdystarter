package com.zdy.mystarter.lambda.impl;

/**
 * todo 使用@FunctionalInterface定义函数式接口
 * Created by Jason on 2020/1/15.
 */
public class CommonFunctionalInterfaceCheck {

    /**
     * todo 多参数无返回
     */
    @FunctionalInterface
    public interface NoReturnMultiParam{
        void method(int first,int second);
    }

    /**
     * todo 无参无返回
     */
    @FunctionalInterface
    public interface NoReturnNoParam{
        void method();
    }

    /**
     * todo 一个参数无返回
     */
    @FunctionalInterface
    public interface NoReturnOneParam{
        void method(int one);
    }

    /**
     * todo 多参数有返回
     */
    @FunctionalInterface
    public interface ReturnMultiParam{
        int method(int first,int second);
    }

    /**
     * todo 无参有返回
     */
    @FunctionalInterface
    public interface ReturnNoParam{
        int method();
    }

    /**
     * todo 一个参数无返回
     */
    @FunctionalInterface
    public interface ReturnOneParam{
        int method(int one);
    }

}
