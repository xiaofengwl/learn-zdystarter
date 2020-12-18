package com.zdy.mystarter;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

/**
 * Created by Jason on 2020/1/13.
 */
public class StartMain {

    /**
     * todo  测试
     * @param args
     */
    public static void main(String[] args){
        paramTest1("",null);
    }

    /**
     * todo 测试参数注解：@
     * @param p1
     * @param p2
     */
    @NotNull
    public static void paramTest1(@Nullable String p1, @NotNull Object p2){
        System.out.println(p1+","+p2);
    }
}
