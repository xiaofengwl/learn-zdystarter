package com.zdy.mystarter.basic.msgconverters.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * TODO 重置一个消息消息转换器
 * <pre>
 *    (1)---接收Http请求，完成http-requestBody-->java-pojo
 *         AbstractMessageConvertMethodArgumentResolver.readWithMessageConvert
 *    (2)---处理http响应，完成java-pojo-->http-responseBody
 *         AbstractMessageConvertMethodProcessor.writeWithMessageConvert
 *
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/9/1 10:39
 * @desc
 */
public class SpecializationHttpMessageConvert extends AbstractHttpMessageConverter<Object> implements GenericHttpMessageConverter<Object>{

    private static Logger logger= LoggerFactory.getLogger(SpecializationHttpMessageConvert.class);

    public SpecializationHttpMessageConvert(){
        //定制一个媒体类型
        super(new MediaType("application","axb", Charset.forName("UTF-8")));
    }
/**
 *  todo 分析整理
 *      Spring 优先执行GenericHttpMessageConverter的canRead、如果没有实现GenericHttpMessageConverter类，则会调用AbstractHttpMessageConverter的canRead
 *      优先执行优先执行GenericHttpMessageConverter的canRead的read,如果没有实现GenericHttpMessageConverter接口，则会调用AbstractHttpMessageConverter的read，read中会调用readInternal
 *  （1）只要控制消息转换器的实现方式，就可以了
 *       方式1：extends AbstractHttpMessageConverter<Object>
 *       方式2：implements GenericHttpMessageConverter<Object>
 *       方式3：extends AbstractHttpMessageConverter<Object> implements GenericHttpMessageConverter<Object>  效果等同于方式1  不推荐
 *
 *  （2）读取：AbstractMessageConvertMethodArgumentResolver.readWithMessageConverts(....)
 *      写出：AbstractMessageConvertMethodProcessor.writeWithMessageConverts(....)
 */
/************************************[AbstractHttpMessageConverter]**************************************************************************/
    /**
     * 内部还是调用了AbstractHttpMessageConverter内置的canRead,其中canRead中调用了supports
     */
    @Override
    protected boolean supports(Class<?> clazz) {
        logger.info("SpecializationHttpMessageConvert-supports");
        return false;
    }

    /**
     * 处理请求逻辑，AbstractHttpMessageConverter内置的read,read调用了readInternal
     */
    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        logger.info("SpecializationHttpMessageConvert-readInternal");
        return null;
    }

    /**
     *  处理响应逻辑，AbstractHttpMessageConverter内置的write,writeInternal
     */
    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        logger.info("SpecializationHttpMessageConvert-writeInternal");
    }
/************************************[GenericHttpMessageConverter]**************************************************************************/
    /**
     * 高优先级，先执行GenericHttpMessageConverter的canRead
     */
    @Override
    public boolean canRead(Type type, @Nullable Class<?> contextClass, @Nullable MediaType mediaType) {
        logger.info("SpecializationHttpMessageConvert-canRead");
        return false;
    }

    /**
     * 高优先级，先执行GenericHttpMessageConverter的read
     */
    @Override
    public Object read(Type type, @Nullable Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        logger.info("SpecializationHttpMessageConvert-read");
        return null;
    }

    /**
     * 高优先级，先执行GenericHttpMessageConverter的canWrite
     */
    @Override
    public boolean canWrite(@Nullable Type type, Class<?> clazz, @Nullable MediaType mediaType) {
        logger.info("SpecializationHttpMessageConvert-canWrite");
        return false;
    }

    /**
     * 高优先级，先执行GenericHttpMessageConverter的write
     */
    @Override
    public void write(Object o, @Nullable Type type, @Nullable MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        logger.info("SpecializationHttpMessageConvert-write");
    }
}
