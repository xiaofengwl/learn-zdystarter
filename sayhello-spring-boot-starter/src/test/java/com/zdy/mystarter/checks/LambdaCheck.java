package com.zdy.mystarter.checks;

import com.alibaba.fastjson.JSON;
import com.zdy.mystarter.lambda.ItemCreatorBlankConstruct;
import com.zdy.mystarter.lambda.ItemCreatorParamContruct;
import com.zdy.mystarter.lambda.impl.CommonFunctionalInterfaceCheck;
import com.zdy.mystarter.lambda.vo.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Lambda用法测试
 * Created by Jason on 2020/1/15.
 * 异常记录：
 *  （1）本测试类执行报错：Junit5无法执行junit4，junit+springboot版本不对应问题。---暂未解决
 */
public class LambdaCheck {

    /**
     * todo 【复杂版本】测试@FunctionalInterface 函数式接口的使用方法
     * 格式：
     *     ()->{}
     * 说明：
     *      ()  描述参数列表
     *      {}  描述方法体
     *      ->  为lambda运算符，读作（goes to）
     */
    public static void check_FunctionalInterface(){

        //todo 无参数无返回
        CommonFunctionalInterfaceCheck.NoReturnNoParam noReturnNoParam=()->{
            System.out.println("noReturnNoParam:method");
        };
        noReturnNoParam.method();

        //todo 一个参数无返回
        CommonFunctionalInterfaceCheck.NoReturnOneParam noReturnOneParam=(int one)->{
            System.out.println("noReturnOneParam:method(int one)，参数"+one);
        };
        noReturnOneParam.method(100);

        //todo 多个参数无返回
        CommonFunctionalInterfaceCheck.NoReturnMultiParam noReturnMultiParam=(int first,int second)->{
            System.out.println("noReturnOneParam:method(int first,int second)，参数"+first+","+second);
        };
        noReturnMultiParam.method(10,20);

        //todo 无参有返回值
        CommonFunctionalInterfaceCheck.ReturnNoParam returnNoParam=()->{
            System.out.println("returnNoParam:method,返回值：100");
            return 100;
        };

        //todo 一个参数有返回值
        CommonFunctionalInterfaceCheck.ReturnOneParam returnOneParam=(int one)->{
            System.out.println("returnNoParam:method(int one),参数："+one+",返回值："+one);
            return one;
        };

        //todo 多个参数有返回值
        CommonFunctionalInterfaceCheck.ReturnMultiParam returnMultiParam=(int first,int second)->{
            System.out.println("returnNoParam:method(int one),参数："+first+","+second+",返回值："+second);
            return first+second;
        };
        returnMultiParam.method(100,200);
    }

    /**
     * todo 【简化版本】测试@FunctionalInterface 函数式接口的使用方法
     * 简化之处：
     *    1.()中的参数类型不用再写了
     *    2.简化参数小括号，如果只有一个参数则可以省略参数小括号。
     *    3.简化方法体大括号，如果方法条只有一条语句，则可以省略方法体大括号。
     *    4.如果方法体只有一条语句，并且是 return 语句，则可以省略方法体大括号
     * 格式：
     *     ()->{}
     * 说明：
     *      ()  描述参数列表
     *      {}  描述方法体
     *      ->  为lambda运算符，读作（goes to）
     */
    public static void check_FunctionalInterface2(){

        //todo 无参数无返回
        CommonFunctionalInterfaceCheck.NoReturnNoParam noReturnNoParam
        =()->System.out.println("noReturnNoParam:method");
        noReturnNoParam.method();

        //todo 一个参数无返回
        CommonFunctionalInterfaceCheck.NoReturnOneParam noReturnOneParam
        = one->System.out.println("noReturnOneParam:method(int one)，参数"+one);
        noReturnOneParam.method(100);

        //todo 多个参数无返回
        CommonFunctionalInterfaceCheck.NoReturnMultiParam noReturnMultiParam
        =(first,second)->System.out.println("noReturnOneParam:method(int first,int second)，参数"+first+","+second);
        noReturnMultiParam.method(10,20);

        //todo 无参有返回值
        CommonFunctionalInterfaceCheck.ReturnNoParam returnNoParam=()->{
            System.out.println("returnNoParam:method,返回值：100");
            return 100;
        };
        returnNoParam.method();

        //todo 一个参数有返回值
        CommonFunctionalInterfaceCheck.ReturnOneParam returnOneParam=one->{
            System.out.println("returnNoParam:method(int one),参数："+one+",返回值："+one);
            return one;
        };
        returnOneParam.method(10);

        //todo 多个参数有返回值
        CommonFunctionalInterfaceCheck.ReturnMultiParam returnMultiParam=(first,second)->{
            System.out.println("returnNoParam:method(int one),参数："+first+","+second+",返回值："+second);
            return first+second;
        };
        returnMultiParam.method(100,200);
    }

    /**
     * todo Lambda 表达式常用示例
     * 语法格式：
     *    方法归属者::方法名     静态方法的归属者为类，普通方法的归属者为对象
     */
    public static void check_FunctionalInterface3(){

        //todo
        CommonFunctionalInterfaceCheck.ReturnOneParam lambda1= one -> doubleNum(one);
        /**
         *   one -> doubleNum(one);
         *   todo 写法等效于
         *   one -> {doubleNum(one);};
         *   todo 就是简化版，隐藏了大括号，且使用当前的方法doubleNum(one)做method()方法体的执行内容
         */
        System.out.println(lambda1.method(3));

        //todo 方法归属者::方法名    静态方法
        CommonFunctionalInterfaceCheck.ReturnOneParam lambda2= LambdaCheck::doubleNum;
        /**
         *   LambdaCheck::doubleNum
         * todo 等效于
         *   one -> doubleNum(one)
         * todo 等效于
         *   one -> {doubleNum(one);};
         */
        System.out.println(lambda2.method(4));


        //todo 方法归属者::方法名    普通方法
        LambdaCheck lambdaCheck=new LambdaCheck();
        CommonFunctionalInterfaceCheck.ReturnOneParam lambda3= lambdaCheck::addTwo;
        /**
         *   lambdaCheck::addTwo
         * todo 等效于
         *   one -> addTwo(one)
         * todo 等效于
         *   one -> {addTwo(one);};
         */
        System.out.println(lambda3.method(6));
    }

    /**
     * todo static 静态方法
     * @param one
     * @return
     */
    public static int doubleNum(int one){
        return one*2;
    }

    /**
     * todo 普通方法
     * @param one
     * @return
     */
    public int addTwo(int one) {
        return one + 2;
    }

    /**
     * todo 作用方式：构造方法的引用
     * 一般我们需要声明接口，该接口作为对象的生成器，
     * 通过 类名::new 的方式来实例化对象，然后调用方法返回对象。
     */
    public static void check_FunctionalInterface4(){

        /**
         * TODO 以下三种方式比较有意思
         */
        //todo 方式1
        ItemCreatorBlankConstruct creator=()->new Item();
        Item item = creator.getItem();
        System.out.println(item.toString());

        //todo 方式2
        ItemCreatorBlankConstruct creator2=Item::new;
        Item item2=creator2.getItem();
        System.out.println(item2);

        //todo 方式3
        ItemCreatorParamContruct creator3=Item::new;
        Item item3=creator3.getItem(1,"xfwl",12);
        System.out.println(item3);

    }

    /**
     * TODO lambda 表达式创建线程
     */
    public static void check_FunctionalInterface5(){

        //todo
        Thread thread1=new Thread(()->{
            for (int i = 0; i <10 ; i++) {
                System.out.println("-->"+i);
            }
        });
        thread1.start();
    }

    /**
     * TODO lambda 遍历集合
     */
    public static void check_FunctionalInterface6(){
        //todo 数据准备
        List<Item> items=new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            ItemCreatorParamContruct creator=Item::new;
            Item item=creator.getItem(i,"xfwl",12+i);
            items.add(item);
        }

        //todo  遍历打印
        items.forEach(System.out::println);
        System.out.println("__________1");

        //todo  遍历处理
        items.forEach(item -> {
            if(item.getId()<6){
                System.out.println("--->"+item);
            }
        });
        System.out.println("__________2");

        //todo 删除集合中的数据
        items.removeIf(item -> item.getId()==6);
        items.forEach(System.out::println);
        System.out.println("__________3");

        //todo 集合内元素的排序
        items.sort((nextItem,currentItem)->nextItem.getId()-currentItem.getId());//正序，反则为倒序
        items.forEach(System.out::println);
        System.out.println("__________4");

        //todo 取A集合中的指定数据，生成集合B
        List<Integer> idList=new ArrayList<>();
        /**
         * todo 集合中stream和parallelstream的区别？
         *  1.parallelStream执行效率要比传统的for循环和stream要快的多。
         *  2.parallelStream无序，stream有序。
         *
         *  todo 总结：
         *    什么时候要用stream或者parallelStream呢？可以从以下三点入手考虑
         *    1.是否需要并行？
         *    2.任务之间是否是独立的？是否会引起任何竞态条件？
         *    3.结果是否取决于任务的调用顺序？
         *
         *  todo 所有如果对集合中的数据顺序没什么要求的话，建议使用parallelStream
         */
        items.parallelStream().forEachOrdered((item)->{
            if(item.getId()<=5){
                idList.add(item.getId());
            }
        });
        System.out.println(idList);
        System.out.println("__________5");
        /**
         * todo JDK8.集合中*stream()后foreach和map的区别？
         * todo 相同点：
         *      1.都是做循环遍历。
         *      2.
         * todo 不同点：
         *      1.map表示我给你一个东西你在还我一个东西,而forEach只是单纯的消费
         *      2.
         * todo 如何使用
         *      1.用map获取当前集合的每一个对象，可以选取该对象一个属性值返回，最终得到该属性值的集合。
         *      2.然后使用forEach()或者forEachOrdered()循环这个属性值集合
         */
        items.parallelStream().map(item->item.getId())
                              .forEachOrdered(System.out::println);//等效于：each->System.out.println(each)

    }

    /**
     * TODO Lambda 表达式中的闭包问题
     */
    public static void check_FunctionalInterface7(){

        //todo
        int num = 10;

        Consumer<String> consumer = ele -> {
            /**
             * todo 说明
             *    这个问题我们在匿名内部类中也会存在，如果我们把注释放开会报错，
             * 告诉我 num 值是 final 不能被改变。这里我们虽然没有标识 num 类型为 final，
             * 但是在编译期间虚拟机会帮我们加上 final 修饰关键字。
             */
            System.out.println(num);
        };

        //num = num + 2;
        consumer.accept("hello");
    }

    /**
     * TODO 测试入口
     * @param args
     */
    public static void main(String[] args){
        //todo
        check_FunctionalInterface6();


    }

}
