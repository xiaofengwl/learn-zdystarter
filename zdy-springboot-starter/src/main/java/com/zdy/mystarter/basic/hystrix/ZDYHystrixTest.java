package com.zdy.mystarter.basic.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.stereotype.Component;
import rx.Observable;
import rx.Subscriber;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * TODO 测试HystrixCommand能否修饰在普通方法上
 * <pre>
 *      @HystrixCommand可以修饰普通方法，但是其所在类都必须一个Bean中
 *      可以使用：
 *      @service
 *      @Componment
 *      等等........
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/31 18:01
 * @desc
 */
@Component
public class ZDYHystrixTest {

    /**
     * 响应式
     * @return
     */
    @HystrixCommand
    public Observable<HystrixVo> getHystrixVo(){
        return Observable.create(new Observable.OnSubscribe<HystrixVo>(){

            @Override
            public void call(Subscriber<? super HystrixVo> observer) {

            }
        });
    }

    /**
     * hystrix熔断降级测试
     * @param p1
     * @return
     */
    @HystrixCommand(fallbackMethod = "hystrixTestFallBack")
    public Map<String,Object> hystrixTest(String p1){
        Map<String,Object> map=new HashMap<>();
        map.put("code","200");
        map.put("msg","info:"+(1/0));
        return map;
    }

    /**
     * 熔断降级备用方法
     * @param p1
     * @return
     */
    public Map<String,Object> hystrixTestFallBack(String p1){
        Map<String,Object> map=new HashMap<>();
        map.put("code","200");
        map.put("msg","info:"+p1);
        return map;
    }

    /**
     * todo 异步执行
     * @param name
     *  异步执行：当执行到注解方法时，会并发异步执行，返回一个Future对象，
     *  后面使用.get()方法来阻塞拿到结果。如果有多个方法时，执行时间就是其中
     *  最长的一个服务的执行时间。
     * @return
     */
    @HystrixCommand(fallbackMethod = "hystrixResponseFallBack",
            groupKey="ZDYHystrixTest",
            commandKey = "asynchystrixResponse"                 //当前执行方法名
            /*,commandProperties = {
                //隔离策略：THREAD-线程隔离策略，SEMAPHORE-信号量隔离策略
                @HystrixProperty(name="execute.isolation.strategy",value="SEMAPHORE")
             }*/
    )
    public Future<String> asynchystrixResponse(String name) {
        System.out.println("asynchystrixResponse....return...Future<String>");
        return  new AsyncResult<String>() {
            @Override
            public String invoke() {
                try {
                    System.out.println("准备休眠3s......异步处理");
                    Thread.currentThread().sleep(3000);
                    System.out.println("睡眠后继续执行......异步处理");
                } catch (InterruptedException e) {
                    System.out.println("休眠异常InterruptedException e......异步处理");
                    e.printStackTrace();
                }
                return "info:hystrixResponse_"+name;
            }
        };
    }


    /**
     * todo 同步的方式。
     * fallbackMethod定义降级
     */
    @HystrixCommand(fallbackMethod = "hystrixResponseFallBack",
            groupKey="ZDYHystrixTest",
            commandKey = "hystrixResponse"               //当前执行方法名
            /*,commandProperties = {
                //隔离策略：THREAD-线程隔离策略，SEMAPHORE-信号量隔离策略
                @HystrixProperty(name="execute.isolation.strategy",value="SEMAPHORE")
            }*/
    )
    public String hystrixResponse(String name) throws Exception{
       try{
           int count=(1/0);
       }catch (Exception e){
           System.out.println("coming to catch....");
           throw e;
       }
        return "info:hystrixResponse_"+name;
    }

    /**
     * 服务降级
     * @return
     */
    public String hystrixResponseFallBack(String name) {
        return "info:hystrixResponseFallBack_"+name;
    }



}
