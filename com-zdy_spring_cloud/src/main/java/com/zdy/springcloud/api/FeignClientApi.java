package com.zdy.springcloud.api;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Random;
import java.util.concurrent.*;

/**
 * TODO 添加主题
 * <pre>
 *     @FeignClient做默认的超时控制
 *     SyncResult 做用户定制的超时控制
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/5/2 10:51
 * @desc
 */
@FeignClient(
        value = "zdy-common-project",
        path = "/feign/timeout",
        fallback = FeignClientApiFallBack.class
)
public interface FeignClientApi {

    /**
     * 测试结论：
     *      AsyncResult<String>   的
     */

    default String feignClient_1(){
        String result="";
        Future<String> future=null;
        try {
            future=getSyncResultValue1();
            System.out.println("设置超时时间："+1000);
            result= future.get(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            future.cancel(true);   //关闭future（关闭异常的监测方法）
            result="InterruptedException";
        } catch (ExecutionException e) {
            result="ExecutionException";
        } catch (TimeoutException|RuntimeException e) {
            result="TimeoutException";
            future.cancel(true);//取消没有执行完的任务，设置为ture说明任务能被中断，否则执行中的任务要完成
        }catch (Exception e){
            future.cancel(true);//关闭future（关闭异常的监测方法）
            result="Exception";
        }
        System.out.println(result);
        return result;
    }
    default Future<String> getSyncResultValue1(){
        Future<String> future=new AsyncResult<String>(){
            /**
             * 此种方式不可以定制抛出异常,感觉get方法中设置的超时参数没有生效，
             * 生效的是：全局的hustrix配置：hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=1500
             * @return
             */
            @Override
            public String invoke(){
                System.out.println("开始做future任务");
                long start = System.currentTimeMillis();
                try {
                    //时间必须小于hystrix的超时配置，否则线程会被中断掉
                    int randomInt=new Random().nextInt(3000);
                    System.out.println("准备休息："+randomInt);
                    Thread.sleep(randomInt);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                long end = System.currentTimeMillis();
                System.out.println("future任务完成，耗时：" + (end - start) + "毫秒");
                return "future任务  over";
            }
            @Override
            public String get(long timeout, TimeUnit unit) throws RuntimeException {
                String result="";
                try{
                    result=invoke();
                }catch (RuntimeException e){
                    result="get...RuntimeException...";
                }catch (Exception e){
                    result="get...Exception...";
                }
                return result;
            }

        };
        return future;
    }

    /**
     * 测试OK-2
     * @return
     */
    default String feignClient_2(){
        String result="";
        ExecutorService exec = Executors.newSingleThreadExecutor();
        //新建future，callable，call  //返回值为String，任何类型都可以
        FutureTask<String> future = new FutureTask<String>(
            new Callable<String>() {
                public String call() {
                    System.out.println("开始做future任务");
                    long start = System.currentTimeMillis();
                    try {
                        //时间必须小于hystrix的超时配置，否则线程会被中断掉
                        int randomInt=new Random().nextInt(3000);
                        System.out.println("准备休息："+randomInt+"ms");
                        System.out.println("当前time:"+System.currentTimeMillis());
                        Thread.sleep(randomInt);
                        System.out.println("休息完成："+System.currentTimeMillis());
                    } catch (InterruptedException e) {
                        System.out.println("休息期间发生了异常："+e.getMessage()+"\n"+System.currentTimeMillis());
                        /**
                         * 说明：
                         *  在外层future.get(超时时间，时间单位）中设置得超时时间比内部线程sleep的时间短的时候，
                         *  是调用了内部线程interrupted的，这就产生了在内部线程Sleep期间，被恶意中断了，就跑出了该异常。
                         *
                         */


                        throw new RuntimeException(e);//HystrixRuntimeException
                    }
                    long end = System.currentTimeMillis();
                    System.out.println("future任务完成，耗时：" + (end - start) + "毫秒");

                    return "future任务  over";
                }
            }
        );
        //加载future
        //exec.execute(future);
        System.out.println("future.submit:"+System.currentTimeMillis());
        exec.submit(future);
        try {
            //future的get()设定超时时间
            System.out.println("设置超时时间："+1000);
            result = future.get(1000, TimeUnit.MILLISECONDS);
            System.out.println("执行结束："+System.currentTimeMillis());
        } catch (InterruptedException e) {
            future.cancel(true);   //关闭future（关闭异常的监测方法）
            // 做出对应异常的反应
            result="InterruptedException";
        } catch (ExecutionException e) {
            future.cancel(true);   //关闭future（关闭异常的监测方法 ，此异常标识方法内部异常）
            // 做出对应异常的反应
            result="ExecutionException";
        } catch (TimeoutException|RuntimeException e) {
            future.cancel(true);   //关闭超时线程
            // 做出对应异常的反应
            result="TimeoutException|RuntimeException";
        } finally {
            exec.shutdown();
        }
        System.out.println(result);
        return result;
    }

    default String feignClient_3(){
        System.out.println("开始做future任务");
        long start = System.currentTimeMillis();
        try {
            //时间必须小于hystrix的超时配置，否则线程会被中断掉
            int randomInt=new Random().nextInt(3000);
            System.out.println("准备休息："+randomInt);
            Thread.sleep(randomInt);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);//HystrixRuntimeException
        }
        long end = System.currentTimeMillis();
        System.out.println("future任务完成，耗时：" + (end - start) + "毫秒");
        System.out.println("feignClient_3_value");
        return "feignClient_3_value";
    }

    @RequestMapping("/1")
    String feignClient_4();


    @HystrixCommand(fallbackMethod = "feignClient_3FallBack")
    default Future<String> getSyncResultValue3(){
        Future<String> future=new AsyncResult<String>(){
            /**
             * 做熔断处理
             * {@inheritDoc}.
             */

            @Override
            public String invoke(){
                int i=1/0;
                return "zdy_springboot_starter_future<String>_timeout";
            }
            @Override
            public String get(long timeout, TimeUnit unit) throws UnsupportedOperationException {
                return invoke();
            }

        };
        return future;
    }
    default String feignClient_3FallBack(){
        return "feignClient_1FallBack";
    }


}
