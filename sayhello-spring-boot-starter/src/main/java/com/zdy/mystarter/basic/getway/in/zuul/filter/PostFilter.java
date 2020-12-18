package com.zdy.mystarter.basic.getway.in.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * TODO 处理分发结果
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/24 17:44
 * @desc
 */
@Component
public class PostFilter extends ZuulFilter{

    private Logger logger= LoggerFactory.getLogger(PostFilter.class);

    /**
     * 处理应答结果
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    /**
     *  zuul过滤器，排序序号：1-第二位
     * @return
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 持续做拦截
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 做分发结果的逻辑处理
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        //获取操作对象
        RequestContext ctx=RequestContext.getCurrentContext();
        InputStream inputStream=ctx.getResponseDataStream();
        String xml="";
        try{
            xml= StreamUtils.copyToString(inputStream, Charset.forName("UTF-8"));
            logger.info("返回报文："+xml);
            ctx.setResponseBody(xml);
            ctx.getResponse().setContentType("application/xml;charset=UTF-8");

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
