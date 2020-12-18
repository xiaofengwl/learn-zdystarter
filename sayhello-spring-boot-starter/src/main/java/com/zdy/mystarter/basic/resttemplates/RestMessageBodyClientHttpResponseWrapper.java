package com.zdy.mystarter.basic.resttemplates;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

/**
 * TODO 定制处理类
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/20 10:31
 * @desc
 */
public class RestMessageBodyClientHttpResponseWrapper implements ClientHttpResponse{

    private final ClientHttpResponse response;

    @Nullable
    private PushbackInputStream pushbackInputStream;

    /**
     * todo 构造器
     * @param response
     */
    public RestMessageBodyClientHttpResponseWrapper(ClientHttpResponse response){
        this.response=response;
    }

    /**
     * 判断是否有消息BODY
     * @return
     * @throws IOException
     */
    public boolean hasMessageBody()throws IOException{
        HttpStatus status=HttpStatus.resolve(getRawStatusCode());
        if(status!=null && (status.is1xxInformational()||status==HttpStatus.NO_CONTENT
                            ||status==HttpStatus.NOT_MODIFIED)){
            return false;
        }
        if(getHeaders().getContentLength()==0){
            return false;
        }
        return true;
    }

    /**
     * 判断是否是空消息体
     * @return
     * @throws IOException
     */
    public boolean hasEmptyMessageBody()throws IOException{
        InputStream body=this.response.getBody();
        if(body.markSupported()){
            body.mark(1);
            if (body.read()==-1){
                return true;
            }else{
                body.reset();
                return false;
            }
        }else{
            this.pushbackInputStream=new PushbackInputStream(body);
            int b=this.pushbackInputStream.read();
            if(b==-1){
                return true;
            }else {
                this.pushbackInputStream.unread(b);
                return false;
            }
        }
    }



    @Override
    public HttpStatus getStatusCode() throws IOException {
        return this.response.getStatusCode();
    }

    @Override
    public int getRawStatusCode() throws IOException {
        return this.response.getRawStatusCode();
    }

    @Override
    public String getStatusText() throws IOException {
        return this.response.getStatusText();
    }

    @Override
    public void close() {
        this.response.close();
    }

    @Override
    public InputStream getBody() throws IOException {
        return (this.pushbackInputStream!=null?this.pushbackInputStream:this.response.getBody());
    }

    @Override
    public HttpHeaders getHeaders() {
        return this.response.getHeaders();
    }
}
