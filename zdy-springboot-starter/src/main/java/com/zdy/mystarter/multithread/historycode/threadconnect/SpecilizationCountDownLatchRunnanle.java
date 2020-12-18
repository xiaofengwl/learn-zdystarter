package com.zdy.mystarter.multithread.historycode.threadconnect;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/10/10 16:40
 * @desc
 */
public class SpecilizationCountDownLatchRunnanle {

    private Integer i=0;
    private CountDownLatch countDownLatch=new CountDownLatch(1);//计数器

    /**
     * 奇数
     */
    public void odd(){
        String name=Thread.currentThread().getName();
        while (i<10){
            if(i%2==1){
                System.out.println(name+"_"+new Date().getTime()+",奇数："+i);
                i++;
                System.out.println(name+"_"+new Date().getTime()+",奇数："+i+"_+1了,唤醒");
                countDownLatch.countDown();
            }else{
                try {
                    System.out.println(name+"_"+new Date().getTime()+",奇数："+i+"_睡眠");
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 偶数
     */
    public void even(){
        String name=Thread.currentThread().getName();
        while (i<10){
            if(i%2==0){
                System.out.println(name+"_"+new Date().getTime()+",偶数："+i);
                i++;
                System.out.println(name+"_"+new Date().getTime()+",偶数："+i+"_+1了,唤醒");
                countDownLatch.countDown();
            }else{
                try {
                    System.out.println(name+"_"+new Date().getTime()+",偶数："+i+"_睡眠");
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 测试入口
     * @param args
     */
    public static void main(String[] args){

        final SpecilizationCountDownLatchRunnanle runnable=new SpecilizationCountDownLatchRunnanle();

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
