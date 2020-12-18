package com.zdy.mystarter.basic.threadpool;

import java.util.concurrent.*;

/**
 * TODO 处理超时问题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/4/28 16:52
 * @desc
 */
public class MethodTimeOutDeal {

    /**
     * TODO 为指定一块的处理逻辑设置超时时间监控
     */
    public void timeOutDeal(){

        //todo 创建一个线程池
        final ExecutorService exec=Executors.newFixedThreadPool(1);
        Callable<String> call=new Callable<String>() {
            @Override
            public String call() throws Exception {
                //todo 要被监控超时的业务逻辑


                return null;
            }
        };
        try {
            //todo 执行
            Future<String> future=exec.submit(call);
            String result=future.get(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            //todo 捕获超时异常
            e.printStackTrace();
        }
        //todo 关闭线程池
        exec.shutdown();
    }


}
