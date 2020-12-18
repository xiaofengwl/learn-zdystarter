package com.zdy.mystarter.basic.zuul;


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
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/9/25 14:59
 * @desc
 */
@Component
public class SpecilizationZuulFallBack implements FallbackProvider {

    /**
     * todo 返回fallback处理哪一个服务。返回的是服务的名称
     */
    @Override
    public String getRoute() {
        return "zdy-common-project";
    }

    /**
     * todo fallback逻辑。优先调用。可以根据异常类型动态决定处理方式。
     */
    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {


        /**
         * todo 定制一个响应对象
         */
        return new ClientHttpResponse(){

            /**
             * todo 设置响应头对象
             * @return
             */
            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders httpHeaders=new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                return httpHeaders;
            }

            /**
             * todo 设置响应体对象
             * @return zuul会将本方法返回的输入流数据读取，并通过HttpServletResponse的输出流输出到客户端。
             * @throws IOException
             */
            @Override
            public InputStream getBody() throws IOException {
                String content = "SpecilizationZuulFallBack InputStream::getBody()";
                return new ByteArrayInputStream(content.getBytes());
            }

            /**
             * todo 设置响应码对象
             * @return ClientHttpResponse的fallback的状态码 返回HttpStatus
             * @throws IOException
             */
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.GATEWAY_TIMEOUT;
            }

            /**
             * todo 设置响应码对象
             * @return ClientHttpResponse的fallback的状态码 返回int
             * @throws IOException
             */
            @Override
            public int getRawStatusCode() throws IOException {
                return this.getStatusCode().value();
            }

            /**
             * todo 设置响应码对象
             * @return ClientHttpResponse的fallback的状态码 返回String
             * @throws IOException
             */
            @Override
            public String getStatusText() throws IOException {
                return this.getStatusCode().getReasonPhrase();
            }

            /**
             * todo 回收资源方法
             * 用于回收当前fallback逻辑开启的资源对象的。
             * 不要关闭getBody方法返回的那个输入流对象。
             */
            @Override
            public void close() {

            }
        };
    }
}
