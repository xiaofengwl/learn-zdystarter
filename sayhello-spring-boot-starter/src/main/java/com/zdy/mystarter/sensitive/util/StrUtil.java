package com.zdy.mystarter.sensitive.util;

/**
 * TODO 字符串处理工具
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/21 14:45
 * @desc
 */
public class StrUtil {

    private static final String EMPTY="";
    private static final String STAR="*";
    private static final String AT="@";

    public StrUtil(){}


    public static boolean isEmpty(String string){
        return null==string||"".equals(string);
    }

    /**
     *
     * @param component
     * @param times
     * @return
     */
    public static String repeat(String component,int times){
        if(!isEmpty(component)&&times>0){
            StringBuffer stringBuffer=new StringBuffer();
            for (int i=0;i<times;i++){
                stringBuffer.append(component);
            }
            return stringBuffer.toString();
        }else{
            return "";
        }
    }

    /**
     * 字符串替换
     * @param original
     * @param middle
     * @param prefixLength
     * @return
     */
    public static String buildString(Object original,String middle,int prefixLength){
        if(null==original){
            return null;
        }else{
            String string=original.toString();
            int stringLength=string.length();
            String prefix="";
            String suffix="";
            if(stringLength>=prefixLength){
                prefix=string.substring(0,prefixLength);
            }else{
                prefix=string.substring(0,stringLength);
            }
            int suffixLength=stringLength-prefix.length()-middle.length();
            if(suffixLength>0){
                suffix=string.substring(stringLength-suffixLength);
            }
            return prefix+middle+suffix;
        }
    }










}
