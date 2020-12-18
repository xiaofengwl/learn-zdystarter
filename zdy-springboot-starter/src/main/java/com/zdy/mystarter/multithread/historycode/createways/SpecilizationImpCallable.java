package com.zdy.mystarter.multithread.historycode.createways;

import java.util.Date;
import java.util.concurrent.Callable;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/10/10 10:15
 * @desc
 */
public class SpecilizationImpCallable implements Callable{

    @Override
    public Object call() throws Exception {
        for (int i=0;i<10;i++){
            System.out.println(i+"-Callable自定义线程正在执行-->"+new Date().getTime());
        }
        return "Callable执行完毕！";
    }
}
