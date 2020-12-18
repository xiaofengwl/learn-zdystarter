package com.zdy.mystarter.basic.msgconverters;

import com.zdy.mystarter.model.UserEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.Nullable;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO自定义http消息转换器
 * <pre>
 *     通过自定义一个消息转换器分析执行原理，从SpringBoot框架的调用入口点开始分析。
 *
 *     方法执行顺序：
 *     1.构造函数，为当前消息转换器设置能够识别的MediaType
 *     2.canRead()，判断是否接入，true-执行read()
 *     3.read()，接入逻辑
 *     4.canWrite()，判断是否接出，true-执行write()
 *     5.write()，接出逻辑
 *
 *
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/2/23 14:17
 * @desc
 */
//@Component  //这种方式也可以为工程添加消息转换器
public class ZDYHttpMessageConverter extends AbstractHttpMessageConverter<Object>
                                    implements GenericHttpMessageConverter<Object>{

    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private static final String DEFAULT_SUBTYPE = "xml";

    private final static List<String> SUPPORTED_MEDIA_TYPES=new ArrayList<>();
    static {
        SUPPORTED_MEDIA_TYPES.add(MediaType.APPLICATION_XML_VALUE);
        SUPPORTED_MEDIA_TYPES.add("application/xml;charset=UTF-8");
    }


    /**
     * TODO 自定义支持的媒体类型
     * 也就是只有此种类型的数据才会被处理
     * 测试结论：
     *      Content-Type  必须匹配上才行
     */
    public ZDYHttpMessageConverter(){
        //super(new MediaType("application", DEFAULT_SUBTYPE, DEFAULT_CHARSET));
    }

    /**
     * TODO 判断是否为支持的类型
     * @param aClass
     * @return
     */
    @Override
    protected boolean supports(Class<?> aClass) {
        return true;
    }

    /**
     * 作用不明-1
     * @param aClass
     * @param httpInputMessage
     * @return
     * @throws IOException
     * @throws HttpMessageNotReadableException
     */
    @Override
    protected Object readInternal(Class<?> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    /**
     * 作用不明-2
     * @param o
     * @param httpOutputMessage
     * @throws IOException
     * @throws HttpMessageNotWritableException
     */
    @Override
    protected void writeInternal(Object o, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {

    }

    /**
     * 判断是否可读，如果true，则进行read()
     * @param type
     * @param aClass
     * @param mediaType
     * @return
     */
    @Override
    public boolean canRead(Type type, @Nullable Class<?> aClass, @Nullable MediaType mediaType) {
        return true;
    }

    /**
     * 拦截读操作-进入
     * @param type
     * @param aClass
     * @param httpInputMessage
     * @return
     * @throws IOException
     * @throws HttpMessageNotReadableException
     */
    @Override
    public Object read(Type type, @Nullable Class<?> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        String temp = StreamUtils.copyToString(httpInputMessage.getBody(), Charset.forName("UTF-8"));
        System.out.println("type = "+type);
        System.out.println("content = "+temp);
        if(type.equals(UserEntity.class)){
            String[] tempArr = temp.split("-");
            //获取执行对应的Controller方法,方法入参类型要匹配
            return null;//new UserEntity(tempArr[0],tempArr[1]);
        }
        /**
         * 只有返回了null,才会去执行canWrite()
         * 注意：
         *     一旦返回了null，然后进入canWrite()，就不会再去执行Controller中方法的逻辑了。
         *  需要在write()方法中自己写逻辑。
         */
        return null;
    }

    /**
     * 判断是否可写，如果true，则进行write()
     * @param type
     * @param aClass
     * @param mediaType
     * @return
     */
    @Override
    public boolean canWrite(@Nullable Type type, Class<?> aClass, @Nullable MediaType mediaType) {
        return false;
    }

    /**
     * 拦截写操作-输出
     * @param o
     * @param type
     * @param mediaType
     * @param httpOutputMessage
     * @throws IOException
     * @throws HttpMessageNotWritableException
     */
    @Override
    public void write(Object o, @Nullable Type type, @Nullable MediaType mediaType, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        //获取响应HEADER
        HttpHeaders headers=httpOutputMessage.getHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        //获取响应BODY
        String out = "hello: xxxxxx";
        httpOutputMessage.getBody().write(out.getBytes());
    }
}
