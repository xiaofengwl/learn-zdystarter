package com.zdy.mystarter.multithread.historycode.threadlocal;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/10/12 10:41
 * @desc
 */
public class Bank {

    ThreadLocal<Integer> t=new ThreadLocal<Integer>(){

        /**
         * 初始化值
         * @return
         */
        @Override
        protected Integer initialValue() {
            return 0;
        }

    };

    public Integer get(){
        return t.get();
    }

    public void set(){
        t.set(t.get()+10);
    }

    public static void main(String[] args) {
        //一个运行逻辑
        Bank bank=new Bank();
        Transfer transfer=new Transfer(bank);
        //2个线程在跑,彼此隔离，每个线程都是单独跑一遍
        Thread t1=new Thread(transfer);
        Thread t2=new Thread(transfer);

        t1.start();
        t2.start();

    }

}
