/*
package com.zdy.mystarter.basic.getway.in.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.zdy.mystarter.basic.getway.in.zuul.entity.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

*/
/**
 * TODO 请求分发处理
 * <pre>
 *     todo 网关接收请求，并做转发
 *     设置：
 *        public String filterType() {
 *          return FilterConstants.ROUTE_TYPE;
 *       }
 *
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/24 17:18
 * @desc
 *//*

@Component
public class DispatcherFilter extends ZuulFilter{

    private Logger logger= LoggerFactory.getLogger(DispatcherFilter.class);

    */
/**
     * todo 过滤器类型选择：
     * pre 为路由前
     * route 为路由过程中
     * post 为路由过程后
     * error 为出现错误的时候
     * 同时也支持static ，返回静态的响应，详情见StaticResponseFilter的实现
     * 以上类型在会创建或添加或运行在FilterProcessor.runFilters(type)
     *//*

    @Override
    public String filterType() {
        return FilterConstants.ROUTE_TYPE;
    }

    */
/**
     * todo zuul过滤器，排序序号：0-第一位
     * 用来过滤器排序执行的
     * @return 排序的序号
     *//*

    @Override
    public int filterOrder() {
        return 0;
    }

    */
/**
     * todo 是否通过这个过滤器，默认为true，改成false则不启用
     *//*

    @Override
    public boolean shouldFilter() {
        return false;
    }

    */
/**
     * todo 过滤器的核心处理逻辑
     * @return
     * @throws ZuulException
     *//*

    @Override
    public Object run() throws ZuulException {
        //获取操作对象
        RequestContext ctx=RequestContext.getCurrentContext();
        String serviceCode="";
        String serviceSence="";
        try{
            //获取请求的xml字符串
            String xml=new BufferedReader(new InputStreamReader(ctx.getRequest().getInputStream()))
                    .lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            logger.info("请求报文："+xml);
            //提取数据,需要根据要求做定制化
            //serviceCode=getKey(xml,"SERVICE_CODE");
            //serviceSence=getKey(xml,"SERVICE_SCENE");
        }catch (Exception e){
            e.printStackTrace();
        }

        */
/**
         * 路由可以通过mybatis从数据库查询进来。
         *//*

        Router router=new Router();
        //模拟参数
        router.setTargetService("http://localhost:8083");  //设置在注册中心中的服务Id
        router.setTargetUrl("/consul/producer/hello");      //设置目标的URI

        //找不到映射关系，则不转发
        if(null==router){
            //需要转成xml格式返回
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(500);
            String xml=getExceptionXml(serviceCode,serviceSence);
            ctx.setResponseBody(xml);
            ctx.getResponse().setContentType("application/xml;charset=UTF-8");
            logger.info("找不到映射关系，则不转发：返回默认应答："+xml);
            return null;
        }

        //找到映射关系
        ctx.put(FilterConstants.SERVICE_ID_KEY,router.getTargetService());
        ctx.put(FilterConstants.REQUEST_URI_KEY,router.getTargetUrl());
        logger.info("开始转发流程:"+router.getTargetService()+router.getTargetUrl());
        return null;
    }

    */
/**
     * 从xml字符串中提取数据
     * @param xml
     * @param keyName
     * @return
     *//*

    private String getKey(String xml, String keyName) {
        String tmp=xml.substring(xml.indexOf(keyName));
        String tmp1=xml.substring(tmp.indexOf(">")+1);
        String target=tmp1.substring(0,tmp1.indexOf("</"));
        return target;
    }

    */
/**
     * 默认应答数据
     * @param serviceCode
     * @param serviceScene
     * @return
     *//*

    private String getExceptionXml(String serviceCode, String serviceScene) {
        String xml="默认响应post应答数据！！！！";
        return xml;
    }
}
*/
