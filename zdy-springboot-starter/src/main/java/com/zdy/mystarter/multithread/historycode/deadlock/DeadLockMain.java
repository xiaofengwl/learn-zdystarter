package com.zdy.mystarter.multithread.historycode.deadlock;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/10/10 15:30
 * @desc
 */
public class DeadLockMain {

    public static void main(String[] args){
        //创建2个线程
        SpecilizationDeadLock lock1=new SpecilizationDeadLock();
        SpecilizationDeadLock lock2=new SpecilizationDeadLock();
        lock2.flag=1;

        Thread thread1=new Thread(lock1,"线程1");
        Thread thread2=new Thread(lock2,"线程2");

        thread1.start();
        thread2.start();

    }
}
