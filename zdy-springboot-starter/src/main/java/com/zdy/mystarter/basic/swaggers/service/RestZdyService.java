package com.zdy.mystarter.basic.swaggers.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO 自定义Rest服务类
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/23 17:28
 * @desc
 */
public class RestZdyService extends AbstractZdyService{

    private static Logger logger = LoggerFactory.getLogger(RestZdyService.class);

    /**
     * 定义一个简单的执行方法
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/common.do")
    public ServiceResponse invoke(Object request) {
        //todo 获取请求和响应对象
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse res = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        //todo 开始流程调度
        ServiceResponse serviceResponse = callFlow(req);

        return serviceResponse;
    }
}
