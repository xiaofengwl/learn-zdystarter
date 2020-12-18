package com.zdy.mystarter.basic.getway.in.zuul.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * TODO 默认熔断处理
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/24 17:02
 * @desc
 */
@Component
public class CommonFallBackProvider implements FallbackProvider{

    private Logger logger= LoggerFactory.getLogger(CommonFallBackProvider.class);

    /**
     * 只会处理指定路由
     * @return
     */
    @Override
    public String getRoute() {
        return "*";
    }

    /**
     * 熔断处理逻辑,设置备用响应对象
     * @param route 路由url
     * @param cause 异常对象
     * @return
     */
    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.SERVICE_UNAVAILABLE;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.SERVICE_UNAVAILABLE.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase();
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                String msg=(cause!=null && cause.getMessage()!=null)
                        ?(String.format("service:%s catch exception:%s",route,cause.getMessage()))
                        :(String.format("service:%s not available,please try again later!",route));
                logger.error(msg);
                String xml=getExceptionXml(99999,"service not available,please try again later!");
                return new ByteArrayInputStream(xml.getBytes("UTF-8"));
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders httpHeaders=new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                return httpHeaders;
            }
        };
    }

    /**
     * 默认应答数据
     * @param i
     * @param s
     * @return
     */
    private String getExceptionXml(int i, String s) {
        String xml="默认响应post应答数据！！！！";
        return xml;
    }
}
