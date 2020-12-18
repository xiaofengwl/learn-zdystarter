package com.zdy.mystarter.basic.msgconverters;

import com.zdy.mystarter.model.UserEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * TODO自定义http消息转换器
 * <pre>
 *     通过自定义一个消息转换器分析执行原理，从SpringBoot框架的调用入口点开始分析。
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/2/23 14:17
 * @desc
 */
//@Component  //这种方式也可以为工程添加消息转换器
public class ESBHttpMessageConverter extends AbstractHttpMessageConverter<UserEntity> {

    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private static final String DEFAULT_SUBTYPE = "xxx-lj";

    /**
     * TODO 自定义支持的媒体类型
     * 也就是只有此种类型的数据才会被处理
     * 测试结论：
     *      Content-Type  必须匹配上才行
     */
    public ESBHttpMessageConverter(){
        super(new MediaType("application", DEFAULT_SUBTYPE, DEFAULT_CHARSET));
    }

    /**
     * TODO 判断是否为支持的类型
     * @param aClass
     * @return
     */
    @Override
    protected boolean supports(Class<?> aClass) {
        // 表明只处理UserEntity类型的参数。
        return true;
    }

    /**
     * TODO 用于读取定义的类型的资源，将其转化为JSONObject对象
     * @param aClass
     * @param httpInputMessage
     * @return
     * @throws IOException
     * @throws HttpMessageNotReadableException
     */
    @Override
    protected UserEntity readInternal(Class<? extends UserEntity> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        String temp = StreamUtils.copyToString(httpInputMessage.getBody(), Charset.forName("UTF-8"));
        String[] tempArr = temp.split("-");

        return new UserEntity(tempArr[0],tempArr[1]);
    }

    /**
     * TODO 用于读取定义的类型的资源，将其转化为JSONObject对象
     * @param httpOutputMessage
     * @param httpOutputMessage
     * @throws IOException
     * @throws HttpMessageNotWritableException
     */
    @Override
    protected void writeInternal(UserEntity userEntity, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {

        //获取响应HEADER
        HttpHeaders headers=httpOutputMessage.getHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        //获取响应BODY
        String out = "hello: " + userEntity.getName() + "-" + userEntity.getAddress();
        httpOutputMessage.getBody().write(out.getBytes());
    }


}
