package com.zdy.mystarter.multithread.historycode.threadconnect;

import java.util.concurrent.Semaphore;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/10/10 17:01
 * @desc
 */
public class SpecilizationSemaphoreRunnable {

    static class Machine implements Runnable{

        private int num=0;
        private Semaphore semaphore;

        public Machine(int num,Semaphore semaphore){
            this.num=num;
            this.semaphore=semaphore;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();//请求机器人
                System.out.println("工人"+this.num+"请求机器，正在使用机器");
                Thread.sleep(1000);
                System.out.println("工人"+this.num+"使用完毕，已经释放机器");
                semaphore.release();//释放机器
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){

        int worker=8;//工人数
        Semaphore semaphore=new Semaphore(3);
        for (int i=0;i<worker;i++){
            new Thread(new Machine(i,semaphore)).start();
        }

    }
}
