package com.zdy.mystarter.basic.adapters;

import javax.servlet.*;
import java.io.IOException;

/**
 * 请求url过滤器-第二个过滤器（为了测试过滤器链路执行过滤器的顺序问题）
 * Created by Jason on 2020/1/8.
 */
public class UrlSecondHandlerFilterAdapter implements Filter {


    /**
     * [执行顺序-1]初始化处理
     * 服务启动的时候会执行init
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("UrlSecondHandlerFilterAdapter...init...");
    }

    /**
     *[执行顺序-2]过滤处理
     * 服务起动后，收到请求会执行doFilter
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("UrlSecondHandlerFilterAdapter...doFilter...");
        //继续执行过滤器链路
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * [执行顺序-3]过滤器销毁处理
     * 停止服务的时候会执行destory
     */
    @Override
    public void destroy() {
        System.out.println("UrlSecondHandlerFilterAdapter...destroy...");
    }
}
