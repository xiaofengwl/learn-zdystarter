package com.zdy.mystarter.basic.filters;

import com.zdy.mystarter.basic.wrappers.ParameterHttpRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * todo 支持请求带有JSON格式数据
 * (1)判断请求的contentType="application/json"
 * (2)解析该JSON类型的数据到然后保存到当前request中。
 * (3)通过装饰者模式将
 *
 * Created by Jason on 2020/1/14.
 */
public class JsonFormatFilter  implements Filter{


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 应该放置到过滤器链路的最后一位
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        ParameterHttpRequestWrapper request=new ParameterHttpRequestWrapper((HttpServletRequest) servletRequest);
        //处理
        //todo  将增强后的request对象加入到过滤链中
        filterChain.doFilter(request,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
