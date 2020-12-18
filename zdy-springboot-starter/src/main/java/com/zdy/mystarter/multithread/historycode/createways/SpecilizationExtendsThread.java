package com.zdy.mystarter.multithread.historycode.createways;

import java.util.Date;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/10/10 9:56
 * @desc
 */
public class SpecilizationExtendsThread extends Thread{

    @Override
    public void run() {
        for (int i=0;i<10;i++){
            System.out.println(i+"-Thread自定义线程正在执行-->"+new Date().getTime());
        }
    }
}
