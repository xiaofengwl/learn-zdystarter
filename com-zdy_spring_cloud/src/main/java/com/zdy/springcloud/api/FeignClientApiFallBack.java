package com.zdy.springcloud.api;

import org.springframework.stereotype.Component;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/5/2 11:49
 * @desc
 */
@Component
public class FeignClientApiFallBack implements FeignClientApi {

    @Override
    public String feignClient_1() {
        return "FeignClientApiFallBack...feignClient_1..value";
    }
    @Override
    public String feignClient_2() {
        return "FeignClientApiFallBack...feignClient_2..value";
    }
    @Override
    public String feignClient_3() {
        return "FeignClientApiFallBack...feignClient_3..value";
    }

    @Override
    public String feignClient_4() {
        return "FeignClientApiFallBack...feignClient_4..value";
    }
}
