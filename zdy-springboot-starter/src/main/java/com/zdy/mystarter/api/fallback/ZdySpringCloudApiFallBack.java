package com.zdy.mystarter.api.fallback;

import com.zdy.mystarter.api.ZdySpringCloudApi;
import org.springframework.stereotype.Component;

/**
 * TODO 服务熔断降级处理
 * <pre>
 *     必须@Component
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/27 9:33
 * @desc
 */
@Component
public class ZdySpringCloudApiFallBack implements ZdySpringCloudApi {
    @Override
    public String helloConsulFeign() {
        return "helloConsulFeign-微服务调用失败，服务熔断降级处理！";
    }

    @Override
    public String apiTest() {
        return "ZdySpringCloudApiFallBack-微服务调用失败，服务熔断降级处理！";
    }

}
