package com.zdy.mystarter.lambda;

/**
 * Lambda接口
 * Created by Jason on 2020/1/15.
 */
public interface IDoSomeThing {

    /**
     * todo 这是接口的普通抽象方法定义，不可以有方法体
     * 补充说明：
     *     实现子类中必须要实现@Override该方法
     * @param object
     * @return
     */
    String check(Object object);

    /**
     * todo 接口中被default修饰的方法，必须要有方法体，
     * 补充：
     *   所有default可以在接口中定义大量含有方法体的方法，且在实现子类中可选择是否@Override
     *
     */
    default void print(){

    }

}
