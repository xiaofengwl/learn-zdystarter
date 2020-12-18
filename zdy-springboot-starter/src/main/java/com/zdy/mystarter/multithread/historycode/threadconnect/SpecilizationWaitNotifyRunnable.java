package com.zdy.mystarter.multithread.historycode.threadconnect;

import java.util.Date;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/10/10 16:01
 * @desc
 */
public class SpecilizationWaitNotifyRunnable {

    private Object obj=new Object();
    private Integer i=0;

    /**
     * 奇数
     */
    public void odd(){
        String name=Thread.currentThread().getName();
        while (i<100){
            synchronized (obj){
                if (i%2==1){
                    System.out.println(name+"_"+new Date().getTime()+",奇数："+i);
                    i++;
                    //唤醒obj上的阻塞线程
                    System.out.println(name+"_"+new Date().getTime()+",奇数："+i+"_+1了,唤醒");
                    obj.notify();
                }else{
                    System.out.println(name+"_"+new Date().getTime()+",奇数："+i+"_睡眠");
                    try {
                        //obj上的线程进入阻塞状态
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 偶数
     */
    public void even(){
        String name=Thread.currentThread().getName();
        while (i<100){
            synchronized (obj){
                if(i%2==0){
                    System.out.println(name+"_"+new Date().getTime()+",偶数："+i);
                    i++;
                    System.out.println(name+"_"+new Date().getTime()+",偶数："+i+"_+1了,唤醒");
                    obj.notify();
                }else{
                    System.out.println(name+"_"+new Date().getTime()+",偶数："+i+"_睡眠");
                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 测试入口
     * @param args
     */
    public static void main(String[] args){

        final SpecilizationWaitNotifyRunnable runnable=new SpecilizationWaitNotifyRunnable();

        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                runnable.odd();
            }
        },"奇数线程");

        Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {
                runnable.even();
            }
        },"偶数线程");

        t1.start();
        t2.start();
    }

}
