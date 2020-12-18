package com.zdy.mystarter.basic.hystrix;

import com.netflix.hystrix.HystrixCommand;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;

/**
 * TODO 编码的方式使用HystrixCommand
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/7/3 14:43
 * @desc
 */
public class ZDYHystrixCommand<T> extends HystrixCommand<T>{

    private RestTemplate restTemplate;

    private Long id;

    private T responseType;

    /**
     *
     * @param setter
     */
    protected ZDYHystrixCommand(Setter setter,RestTemplate restTemplate,Long id,T responseType) {
        super(setter);
        this.restTemplate=restTemplate;
        this.id=id;
        this.responseType=responseType;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    @Override
    protected T run() throws Exception {
        return (T)restTemplate.getForObject("url",responseType.getClass(),id);
    }

    /**
     * todo 调用方式：
     *  同步调用：
     *      HystrixVo vo=new ZDYHystrixCommand(restTemplate,id,hystrixVo).execute()
     *
     *  异步调用：
     *      Future<HystrixVo> future=new ZDYHystrixCommand(restTemplate,id,hystrixVo).queue();
     *
     *
     *
     */


}




