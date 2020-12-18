package com.zdy.mystarter.api.fallback;

import com.zdy.mystarter.api.ZdySpringCloudApi;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * TODO 降级处理工厂处理类
 * <pre>
 *     必须@Component
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/27 10:02
 * @desc
 */
@Component
public class ZdySpringCloudApiFallBackFactory implements FallbackFactory<ZdySpringCloudApi> {

    private Logger logger = LoggerFactory.getLogger(ZdySpringCloudApiFallBackFactory.class);

    /**
     *
     * @param cause
     * @return
     */
    @Override
    public ZdySpringCloudApi create(Throwable cause) {
        return new ZdySpringCloudApi(){

            @Override
            public String apiTest() {
                return "ZdySpringCloudApiFallBackFactory-微服务调用失败，服务熔断降级处理！";
            }

            @Override
            public String helloConsulFeign() {
                return "helloConsulFeign-微服务调用失败，服务熔断降级处理！";
            }
        };
    }
}
