package com.zdy.mystarter.multithread;

import com.zdy.mystarter.multithread.historycode.createways.SpecilizationExtendsThread;
import com.zdy.mystarter.multithread.historycode.createways.SpecilizationImpRunnable;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/10/10 9:54
 * @desc
 */
public class ThreadMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //创建自定义线程
        //方式1、继承Thread
        SpecilizationExtendsThread myThread=new SpecilizationExtendsThread();
        myThread.start();
        //方式2、实现Runnable
        Thread runable=new Thread(new SpecilizationImpRunnable());
        runable.start();
        //方式3、实现Callable
        //FutureTask task=new FutureTask(new SpecilizationImpCallable());
        //Thread callable=new Thread(task);
        //callable.start();
        //System.out.println(task.get());

        //方式4、线程池-Executor
        //通过Executors创建线程池
        ExecutorService executorService= Executors.newFixedThreadPool(10);
        executorService.execute(new SpecilizationImpRunnable());





        //主线程循环打印
        for (int i=0;i<10;i++){
            System.out.println(i+"-main主线程正在执行-->"+new Date().getTime());
        }
    }

}
