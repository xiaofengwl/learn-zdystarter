package com.zdy.mystarter.basic.adapters;

import javax.servlet.*;
import java.io.IOException;

/**
 * 请求url过滤器
 * Created by Jason on 2020/1/8.
 * 依赖于Servlet容器，通过函数回调实现，可以对所有请求生效。
 *
 * 实现过滤器的方式：
 * （1）实现Servlet的过滤器接口：javax.servlet.Filter
 *
 * 说明：
 *  经过测试发现：
 *      过滤器的init()   在启动服务时会被调用。
 *      过滤器的doFilter() 在服务运行阶段收到请求时会被调用。
 *      过滤器的destory()  在关闭服务时会被调用。
 *
 */
public class UrlHandlerFilterAdapter implements Filter{

    /**
     * [执行顺序-1]初始化处理
     * 服务启动的时候会执行init
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("UrlHandlerFilterAdapter...init...");
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
        System.out.println("UrlHandlerFilterAdapter...doFilter...");
        //继续执行过滤器链路
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * [执行顺序-3]过滤器销毁处理
     * 停止服务的时候会执行destory
     */
    @Override
    public void destroy() {
        System.out.println("UrlHandlerFilterAdapter...destroy...");
    }
}
