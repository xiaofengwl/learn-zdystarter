package com.zdy.mystarter.tools;

import com.alibaba.fastjson.JSONObject;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.net.URISyntaxException;

/**
 * TODO 工具类
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/2/8 12:42
 * @desc
 */
public class ToolUtils {


    /**
     * 分布式多工程获取指定工程的路径
     * todo 处理路径中文乱码问题
     */
    public String getPath(){
        String path= null;
        try {
            //这种方式获取的路径上的中文是正常的
            path = ToolUtils.class.getClassLoader().getResource("").toURI().getPath();
            //如果是分布式工程，可以指定路径来获取其他子工程的路径
            path = ToolUtils.class.getClassLoader().getResource("bizs/").toURI().getPath();
            //
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return path;
    }


    /**
     * todo 判断当前字符串是否是JSON格式
     * @param str
     * @return
     */
    public static boolean isJsonStr(String str){
        boolean flag=true;
        try{
            String bodyStr=str.replaceAll("\n","")
                              .replaceAll("\t","")
                              .replaceAll(" ","");
            System.out.println("bodyStr="+bodyStr);

            JSONObject root=new JSONObject().parseObject(bodyStr);

        }catch(Exception e){
            flag=false;
        }
        return flag;
    }

    /**
     * todo 判断字符串是否是xml格式
     * @param str
     * @return
     */
    public static boolean isXmlStr(String str){
        //Pattern p = Pattern.compile("^\\<**\\>$");
        //Matcher m = p.matcher(str);
        //return m.matches();

        boolean flag = true;
        try {
            String bodyStr=str.replaceAll("\n","")
                    .replaceAll("\t","");

            DocumentBuilderFactory documentBuilderFactory =  DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            builder.parse( new InputSource( new StringReader( str )));
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }





}
