package com.zdy.mystarter.multithread.historycode.threadsafe;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/10/10 11:27
 * @desc
 */
public class TicketSaleMain {

    public static void main(String[] args) {

        Ticket ticket=new Ticket();
        Thread thread1=new Thread(ticket,"A窗口");
        Thread thread2=new Thread(ticket,"B窗口");
        Thread thread3=new Thread(ticket,"C窗口");

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
