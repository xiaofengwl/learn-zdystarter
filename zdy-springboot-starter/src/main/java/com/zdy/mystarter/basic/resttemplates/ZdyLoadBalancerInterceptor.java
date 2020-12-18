package com.zdy.mystarter.basic.resttemplates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

import java.io.IOException;

/**
 * TODO @LoadBalanced 负载均衡拦截器
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/27 16:10
 * @desc
 */
public class ZdyLoadBalancerInterceptor implements ClientHttpRequestInterceptor{

    /**
     * 日志
     */
    private Logger logger= LoggerFactory.getLogger(ZdyLoadBalancerInterceptor.class);

    /**
     * @param request   the request, containing method, URI, and headers
     * @param body      the body of the request
     * @param execution the request execution
     * @return the response
     * @throws IOException in case of I/O errors
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest request,
                                        byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        logger.info("进入LoadBalancer的拦截器....");
        return execution.execute(request,body);
    }






}
