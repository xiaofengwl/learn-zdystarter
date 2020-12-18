package com.zdy.mystarter.multithread.historycode.threadlocal;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/10/12 10:46
 * @desc
 */
public class Transfer implements Runnable {

    Bank bank;

    public Transfer(Bank bank){
        this.bank=bank;
    }

    @Override
    public void run() {
        for (int i = 0; i <10; i++) {
            bank.set();
            System.out.println(Thread.currentThread()+"-->"+bank.get());
        }
    }
}
