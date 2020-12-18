package com.zdy.mystarter.config.httpretry;

import io.netty.channel.ConnectTimeoutException;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

/**
 * TODO Http请求重连控制
 * <pre>
 *     1：该类的作用
 *      1）请求计数，当发生异常的时候，如果重试次数大于某个值，则重连结束
 *      2）当且仅当是可恢复的异常，才能进行重连
 *    2：该类的具体实现
 *       我们通过DefaultHttpClient可以知道，HttpRequestRetryHandler的实现者为DefaultHttpRequestRetryHandler
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/10/29 9:36
 * @desc
 */
public class SpecilizationHttpRequestRetryHandler implements HttpRequestRetryHandler{

    private int retryCount;

    public SpecilizationHttpRequestRetryHandler(int retryCount){
        this.retryCount=retryCount;
    }

    /**
     * 是否重连的控制开关
     * @param exception
     * @param executionCount
     * @param httpContext
     * @return
     */
    @Override
    public boolean retryRequest(IOException exception,
                                int executionCount,
                                HttpContext httpContext) {
        if(executionCount>=retryCount){ //在配置的超时时间内，如果已经重试了N次，那么就重试
            return false;
        }
        if(exception instanceof NoHttpResponseException){//如果服务器丢掉了连接，那么重试
            return true;
        }
        if(exception instanceof SSLHandshakeException){//不要重试SSL握手异常
            return false;
        }
        if(exception instanceof SSLException){//SSL握手异常
            return false;
        }
        if(exception instanceof InterruptedIOException){//超时
            return false;
        }
        if(exception instanceof UnknownHostException) {//目标服务不可达
            return false;
        }
        if(exception instanceof ConnectTimeoutException){//连接被拒绝
            return false;
        }

        HttpClientContext clientContext= HttpClientContext.adapt(httpContext);
        HttpRequest request=clientContext.getRequest();
        //如果请求是幂等性的，就再次尝试
        if(!(request instanceof HttpEntityEnclosingRequest)){
            return true;
        }
        return false;
    }
}
