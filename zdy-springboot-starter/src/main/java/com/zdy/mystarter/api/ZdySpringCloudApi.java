package com.zdy.mystarter.api;

import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * TODO 微服务：zdy-springcloud 的接口
 * <pre>
 *     特别说明：
 *     feign内置了hystrix
 *     服务降级的2种方式：
 *          fallback         无法处理降级失败的原因  优先级高于下面的方式
 *          fallbackFactory  可以做细节处理
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/26 18:41
 * @desc
 */
@FeignClient(value = "zdy-springcloud",
             path = "/consul/producer"
             //,fallback=ZdySpringCloudApiFallBack.class       //异常的时候做降级处理
        //,fallbackFactory = ZdySpringCloudApiFallBackFactory.class
)
public interface ZdySpringCloudApi {

    @RequestMapping("/hello3")
    String apiTest();

    @RequestMapping("/hello")
    String helloConsulFeign();

    @RequestMapping("/hello2")
    default String helloConsul(){
        String result="";
        try {
            Future<String> future=getConsulValue();
            result= future.get(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            result="InterruptedException";
        } catch (ExecutionException e) {
            result="ExecutionException";
        } catch (TimeoutException e) {
            result="TimeoutException";
        }catch (Exception e){
            result="Exception";
        }
        return result;
    }
   default Future<String> getConsulValue(){
        Future<String> future=new AsyncResult<String>(){

            /**
             * {@inheritDoc}.
             */
            @Override
            public String invoke() {
                return "zdy_springboot_starter_future<String>_timeout";
            }
        };
        return future;
    }
    default String getConsulValueFallBack(){
        return "getConsulValueFallBack_value";
    }
}
