package com.zdy.mystarter.multithread.historycode.deadlock;

import java.util.Date;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/10/10 15:29
 * @desc
 */
public class SpecilizationDeadLock implements Runnable{

    private static Object obj1=new Object();//定义成静态变量，使线程可以共享实例
    private static Object obj2=new Object();//定义成静态变量，使线程可以共享实例

    public int flag=0;

    @Override
    public void run() {
        String name=Thread.currentThread().getName();
        System.out.println(name+","+new Date().getTime());
        if(flag==0){
            System.out.println(name+",flag:"+flag);
            synchronized (obj1){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (obj2){
                    System.out.println(name+",flag:"+flag);
                }
            }
        }

        if(flag==1){
            System.out.println(name+",flag:"+flag);
            synchronized (obj2){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (obj1){
                    System.out.println(name+",flag:"+flag);
                }
            }
        }
    }
}
