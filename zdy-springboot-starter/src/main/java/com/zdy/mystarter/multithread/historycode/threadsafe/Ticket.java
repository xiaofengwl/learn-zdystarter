package com.zdy.mystarter.multithread.historycode.threadsafe;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO 测试同步代码块和同步方法、同步锁
 * <pre>
 *     synchronized、Lock
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/10/10 11:23
 * @desc
 */
public class Ticket implements Runnable {

    private int tickNum=100;
    //定义资源目标
    private Object target=new Object();

    //定义锁对象：构造函数参数为线程是否公平获取锁true-公平，false-不公平，即由某个线程独占，默认false
    Lock lock=new ReentrantLock(true);

    @Override
    public void run() {
        String name=Thread.currentThread().getName();
        while (true){
            if(tickNum>0){
                //1、调用同步代码块
                //run_block();
                //2、调用同步方法
                //run_method();
                //3、调用同步锁
                run_lock();
            }else{
                System.out.println("线程"+name+"停止售票！！！");
                break;
            }
        }
    }

    /**
     * 同步锁
     */
    private void run_lock(){
        //加锁
        lock.lock();
        try{
            if(tickNum>0){
                String name=Thread.currentThread().getName();
                //1、模拟出票时间
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //2、打印进程号和票号，票数减1
                System.out.println("线程"+name+"售票："+tickNum--);
            }
        }finally {
            //释放锁
            lock.unlock();
        }
    }

    /**
     * 同步代码块
     */
    private void run_block() {
        //同步代码块
        synchronized (target){
            if(tickNum>0){
                String name=Thread.currentThread().getName();
                //1、模拟出票时间
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //2、打印进程号和票号，票数减1
                System.out.println("线程"+name+"售票："+tickNum--);
            }
        }
    }

    /**
     * 同步方法
     */
    private synchronized void run_method(){
        if(tickNum>0){
            String name=Thread.currentThread().getName();
            //1、模拟出票时间
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //2、打印进程号和票号，票数减1
            System.out.println("线程"+name+"售票："+tickNum--);
        }
    }

}
