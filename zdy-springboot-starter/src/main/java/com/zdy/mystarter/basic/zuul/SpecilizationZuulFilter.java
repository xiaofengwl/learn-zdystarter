package com.zdy.mystarter.basic.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/9/24 16:59
 * @desc
 */
@Component
public class SpecilizationZuulFilter extends ZuulFilter{
    private Logger logger= LoggerFactory.getLogger(SpecilizationZuulFilter.class);
    /**
     * todo 定制执行时间节点：
     * todo 方法返回字符串数据，代表当前过滤器的类型。可选值有-pre, route, post, error。
     * (1)pre - 前置过滤器，在请求被路由前执行，通常用于处理身份认证，日志记录等；
     * (2)route - 在路由执行后，服务调用前被调用；
     * (3)error - 任意一个filter发生异常的时候执行或远程服务调用没有反馈的时候执行（超时），通常用于处理异常；
     * (4)post - 在route或error执行后被调用，一般用于收集服务信息，统计服务性能指标等，也可以对response结果做特殊处理。
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * todo 定制同类型过滤器下的执行优先级
     * todo 返回int数据，用于为同filterType的多个过滤器定制执行顺序，返回值越小，执行顺序越优先。
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * todo 当前过滤器的开关
     * todo 返回boolean数据，代表当前filter是否生效。
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * todo 定制过滤逻辑
     * todo 具体的过滤执行逻辑。如pre类型的过滤器，可以通过对请求的验证来决定是否将请求路由到服务上；如post类型的过滤器，可以对服务响应结果做加工处理（如为每个响应增加footer数据）。
     */
    @Override
    public Object run() throws ZuulException {
        // 通过zuul，获取请求上下文
        RequestContext rc = RequestContext.getCurrentContext();
        HttpServletRequest request = rc.getRequest();

        logger.info("LogFilter1.....method={},url={}",
                request.getMethod(),
                request.getRequestURL().toString());
        // 可以记录日志、鉴权，给维护人员记录提供定位协助、统计性能
        return null;
    }
}