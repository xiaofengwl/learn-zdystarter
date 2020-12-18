package com.zdy.mystarter.basic.handlers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zdy.mystarter.annotations.MessageField;
import com.zdy.mystarter.annotations.ParameterAnntation;
import com.zdy.mystarter.tools.DataToMap;
import com.zdy.mystarter.tools.ToolUtils;
import com.zdy.mystarter.vo.annotation.MessageVo;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.core.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO 解析报文数据，转换成对应类型的实体对象
 * <pre>
 *     主要是根据报文的指定类型，然后解析该报文数据，赋值给对应的实体，
 *     最后返回一个封装了报文数据的实体对象
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/1/17 14:25
 * @desc
 */
public class MessageAnalysisTransformHandler {

    /**
     * todo 处理参数注解：ParameterAnntation
     * @param annotation       注解类型
     * @param methodParameter  方法参数
     * @param request          http请求
     * @param <T>              范型_回自动根据传入类型做匹配
     * @return
     */
    public static <T> MessageVo analysisParameter(ParameterAnntation annotation,
                                          MethodParameter methodParameter,
                                          HttpServletRequest request,
                                          T parameterType,
                                          String bodyStr){
        /**
         * 判断bodyStr是JSON串还是xml格式
         * 判断规则：
         *     xml格式：字符串首尾是<和>
         *     json格式：使用阿里的fastjson做判断
         */
        /*StringEscapeUtils.unescapeJava(bodyStr);*/
        bodyStr= StringEscapeUtils.unescapeJava(bodyStr);
        if(ToolUtils.isJsonStr(bodyStr)){
            //将JSON字符串转换为Map再做处理
            Map bodyMap= DataToMap.parseJsonToMap(bodyStr);
            return analysisJSONAndXmlParameter(bodyMap,0,annotation,methodParameter,request,parameterType,bodyStr);
        }else if(ToolUtils.isXmlStr(bodyStr)){
            //将XML字符串转换为Map再做处理
            Map bodyMap=DataToMap.parseXmlToMap2(bodyStr);
            return analysisJSONAndXmlParameter(bodyMap,1,annotation,methodParameter,request,parameterType,bodyStr);
        }else{
            //其他格式的数据，待扩展
            System.out.println("除json和xml格式的请求体内容，暂不支持其他格式的内容!!!");
        }
        return null;
    }

    /**
     * 解析json和Xml数据格式
     * Json格式如下：---------------------测试通过-----------------------
     * //格式-1-已支持
     * {
     *    "mServiceCode":"2000101",
     *    "mServiceMsg":"消息查询",
     *    "body":{
     *       "bodyId":"0001",
     *       "bodyName":"报文体名称",
     *       "tId":"10",
     *       "tName":"小风微凉",
     *       "tSex":"男",
     *       "tAddress":"深圳市宝安区",
     *       "tCourseId":"100"
     *       }
     *  }
     * 或者
     * //格式-2-已支持
     * {
     *    "mServiceCode":"2000101",
     *    "mServiceMsg":"消息查询",
     *    "body":{
     *       "bodyId":"0001",
     *       "bodyName":"报文体名称",
     *       "teacher":{
     *            "tId":"10",
     *            "tName":"小风微凉",
     *            "tSex":"男",
     *            "tAddress":"深圳市宝安区",
     *            "tCourseId":"100"
     *       },
     *       	"list":["武汉","长沙","深圳"],
     *        "teacherList":[
     *            {
     *                "tId":"20",
     *                "tName":"小风微凉2",
     *                "tSex":"男",
     *                "tAddress":"深圳市宝安区",
     *                "tCourseId":"100"
     *            },
     *            {
     *                "tId":"30",
     *                "tName":"小风微凉3",
     *                "tSex":"男",
     *                "tAddress":"深圳市宝安区",
     *                "tCourseId":"100"
     *            }
     *        ]
     *    }
     * }
     *
     *  Xml格式如下：---------------------测试通过-----------------------
     *  //格式-1-已支持
     *   < ?xml version="1.0" encoding="UTF-8"?>
     *   <service>
     *      <HEAD>
     *           <mServiceCode>2000101</mServiceCode>
     *           <mServiceMsg>消息查询</mServiceMsg>
     *      </HEAD>
     *      <mServiceBody>
     *           <bodyId>0001</bodyId>
     *           <bodyName>报文体名称</bodyName>
     *           <tId>10</tId>
     *          <tName>小风微凉</tName>
     *          <tSex>男</tSex>
     *          <tAddress>深圳市宝安区</tAddress>
     *          <tCourseId>100</tCourseId>
     *      </mServiceBody>
     *   </service>
     *
     * @param bodyMap           map数据源
     * @param type              字段属性类型：0-jsonField,1-xmlField
     * @param annotation        注解对象
     * @param methodParameter   方法参数对象
     * @param request           http请求对象
     * @param parameterType     参数类型
     * @param bodyStr           请求报文
     * @param <T>               泛型
     * @return
     */
    private static <T> MessageVo analysisJSONAndXmlParameter(Map bodyMap,
                                                             int type,
                                                             ParameterAnntation annotation,
                                                             MethodParameter methodParameter,
                                                             HttpServletRequest request,
                                                             T parameterType,
                                                             String bodyStr){

        String mServiceCode="";     //报文头-交易码
        String mServiceMsg="";      //报文头-交易信息
        Map mServiceBody= null;              //报文体

        //todo 开始深入解析-json-定制处理
        if(type==0){
            mServiceCode=(String)bodyMap.get("mServiceCode");
            mServiceMsg=(String)bodyMap.get("mServiceMsg");
            mServiceBody=(Map)bodyMap.get("mServiceBody");              //报文体
        }else if(type==1){
            //todo 开始深入解析-xml-定制处理
            //获取报文头结点
            mServiceCode= (String) ((Map)bodyMap.get("HEAD")).get("mServiceCode");
            mServiceMsg = (String) ((Map)bodyMap.get("HEAD")).get("mServiceMsg");
            mServiceBody= (Map)bodyMap.get("mServiceBody");
            //return new MessageVo<>();
        }

        //todo 数据源
        MessageVo<T> msgVo =new MessageVo<>();
        msgVo.setMServiceCode(mServiceCode);
        msgVo.setMServiceMsg(mServiceMsg);

        //todo 通用解析操作
        analysisParameterOperation(bodyMap,type,parameterType,mServiceBody);
        //todo 压入解析结果
        msgVo.setMServiceBody(parameterType);
        return msgVo;
    }

    /**
     * todo 通用解析操作
     * 说明：
     *      目前仅支持普通单个数据类型，List<普通类型>，List<引用类型>
     *
     * @param bodyMap       map数据源
     * @param type          字段属性类型：0-jsonField,1-xmlField
     * @param parameterType 参数类型
     * @param <T>           泛型
     * @return
     */
    private static <T> T analysisParameterOperation(Map bodyMap,int type,T parameterType,Map mServiceBody){

        //todo 反射处理报文体_业务实体对象
        Class cls=parameterType.getClass();
        //设置私有属性的访问权限
        Field[] fields=cls.getDeclaredFields();
        //Field.setAccessible(fields,true);
        for(Field field:fields){
            String fieldType = field.getType().getSimpleName();
            String fieldSetName = parSetName(field.getName());
            Method fieldSetMet =null;
            try {
                fieldSetMet = cls.getMethod(fieldSetName, field.getType());
            } catch (NoSuchMethodException e) {
                fieldSetMet=null;
                System.out.println(fieldSetName+"方法不存在");
            }
            if(null!=fieldSetMet){
                //判断业务实体上是否有注解@MessageField,且获取该属性上的指定注解值，再做匹配
                for (Annotation annotation1:field.getAnnotations()){
                    if(annotation1 instanceof MessageField){
                        //获取jsonField值
                        String jsonField="";
                        switch(type){
                            case 0:jsonField=((MessageField) annotation1).jsonField();break;
                            case 1:jsonField=((MessageField) annotation1).xmlField();break;
                            default:break;
                        }
                        boolean isReference=((MessageField) annotation1).isReference();
                        /**
                         * todo:目前仅支持二级引用，待递归优化
                         */
                        if(isReference){//引用类型
                            System.out.println(field+",");
                            Class clazz=field.getType();
                            Object obj=null;
                            try {
                                obj = clazz.newInstance();





                            } catch (InstantiationException|IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            Field[] fields1=clazz.getDeclaredFields();

                            for (Field field1:fields1){
                                for (Annotation annotation2:field1.getAnnotations()){
                                    if(annotation2 instanceof MessageField){
                                        String fieldType1 = field1.getType().getSimpleName();
                                        String fieldSetName1 = parSetName(field1.getName());
                                        Method fieldSetMet1 =null;
                                        try {
                                            fieldSetMet1 = clazz.getMethod(fieldSetName1, field1.getType());
                                        } catch (NoSuchMethodException e) {
                                            fieldSetMet1=null;
                                            System.out.println(fieldSetName1+"方法不存在");
                                        }
                                        String refField1="";
                                        if(type==0){
                                            refField1=((MessageField) annotation2).jsonField();
                                        }else if(type==1){
                                            refField1=((MessageField) annotation2).xmlField();
                                        }
                                        //需要将报文中的对应值做一次类型转换，否则反射方法执行异常
                                        Object val1=typeChange(field1,((Map<String,Object>)mServiceBody.get(jsonField)).get(refField1));

                                        if(null!=val1){
                                            try {
                                                fieldSetMet1.invoke(obj, val1);
                                            } catch (IllegalAccessException|InvocationTargetException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }
                            }
                            System.out.println(obj);
                            //设置
                            try {
                                fieldSetMet.invoke(parameterType, obj);
                            } catch (IllegalAccessException|InvocationTargetException e) {
                                e.printStackTrace();
                                System.out.println("反射执行方法"+fieldSetMet+"异常");
                            }
                        }else{//todo 非引用类型  仅支持基础的数据类型,暂不支持map,list
                            boolean isCollection=((MessageField) annotation1).isCollection();
                            boolean isObjCollection=((MessageField) annotation1).isObjCollection();
                            //todo 是否是集合格式
                            if(isCollection){
                                //需要判断改集合中的对象是引用类型还是普通类型
                                System.out.println("isCollection="+isCollection);
                                if(isObjCollection){//List<引用类型>
                                    System.out.println("isObjCollection="+isObjCollection);
                                    try {
                                        //获取指定类
                                        Class listType= ((MessageField) annotation1).type().newInstance().getClass();
                                        Object obj=null;
                                        //收集信息的集合
                                        List objList=new ArrayList();
                                        //从body中获取信息
                                        JSONArray jsonArray= (JSONArray) mServiceBody.get(jsonField);
                                        for (Object o : jsonArray) {
                                            //解析引用对象中的内容
                                            obj = listType.newInstance();
                                            Field[] fields1=listType.getDeclaredFields();

                                            for (Field field1:fields1){
                                                for (Annotation annotation2:field1.getAnnotations()){
                                                    if(annotation2 instanceof MessageField){
                                                        String fieldType1 = field1.getType().getSimpleName();
                                                        String fieldSetName1 = parSetName(field1.getName());
                                                        Method fieldSetMet1 =null;
                                                        try {
                                                            fieldSetMet1 = listType.getMethod(fieldSetName1, field1.getType());
                                                        } catch (NoSuchMethodException e) {
                                                            fieldSetMet1=null;
                                                            System.out.println(fieldSetName1+"方法不存在");
                                                        }
                                                        String refField1="";
                                                        if(type==0){
                                                            refField1=((MessageField) annotation2).jsonField();
                                                        }else if(type==1){
                                                            refField1=((MessageField) annotation2).xmlField();
                                                        }
                                                        //需要将报文中的对应值做一次类型转换，否则反射方法执行异常
                                                        Object val1=typeChange(field1,((JSONObject)o).get(refField1));

                                                        if(null!=val1){
                                                            try {
                                                                fieldSetMet1.invoke(obj, val1);
                                                            } catch (IllegalAccessException|InvocationTargetException e) {

                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            //压入集合中
                                            objList.add(obj);
                                        }
                                        fieldSetMet.invoke(parameterType, objList);

                                    } catch (InstantiationException|IllegalAccessException|InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                }else{//List<普通类型>
                                    List<Object> commonList=(List<Object>)mServiceBody.get(jsonField);
                                    if(null!=commonList){
                                        //field.set(parameterType,val);//提示权限不足
                                        //todo 改使用setter方法
                                        try {
                                            fieldSetMet.invoke(parameterType, commonList);
                                        } catch (IllegalAccessException|InvocationTargetException e) {
                                        }
                                    }
                                }
                            }else{
                                //遍历报文体中内容，做规则匹配
                                Object val=typeChange(field,mServiceBody.get(jsonField));
                                if(null!=val){
                                    //field.set(parameterType,val);//提示权限不足
                                    //todo 改使用setter方法
                                    try {
                                        fieldSetMet.invoke(parameterType, val);
                                    } catch (IllegalAccessException|InvocationTargetException e) {
                                        e.printStackTrace();
                                        System.out.println("反射执行方法"+fieldSetMet+"异常");
                                    }
                                }
                                System.out.println(field+","+val);
                            }
                        }

                    }
                }
            }

        }
        return parameterType;
    }


    /**
     * 将Field中的数据按照实体属性的类型做转换
     * @param field
     * @param val
     * @return
     */
    private static Object typeChange(Field field,Object val){
        Object retObj=null;
        String value=val+"";
        //类型提取
        String fieldType = field.getType().getSimpleName();
        if ("String".equals(fieldType)) {
            retObj=value;
        } else if ("Date".equals(fieldType)) {
            retObj=value;
        } else if ("Integer".equals(fieldType) || "int".equals(fieldType)) {
            retObj = Integer.valueOf(value);
        } else if ("Long".equalsIgnoreCase(fieldType)) {
            retObj = Long.parseLong(value);
        } else if ("Double".equalsIgnoreCase(fieldType)) {
            retObj = Double.parseDouble(value);
        } else if ("Boolean".equalsIgnoreCase(fieldType)) {
            retObj = Boolean.parseBoolean(value);
        } else {
            System.out.println("not supper type" + fieldType);
        }
        return retObj;
    }



    /*
     * todo 判断是否存在某属性的 set方法
     *
     */
    private static boolean checkSetMet(Method[] methods, String fieldSetMet) {
        for (Method met : methods) {
            if (fieldSetMet.equals(met.getName())) {
                return true;
            }
        }
        return false;
    }

    /*
     * todo 拼接某属性的 get方法
     *
     */
    private static String parGetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        return "get" + fieldName.substring(0, 1).toUpperCase()
                + fieldName.substring(1);
    }
    /*
     * todo 拼接在某属性的 set方法
     *
     */
    private static String parSetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        return "set" + fieldName.substring(0, 1).toUpperCase()
                + fieldName.substring(1);
    }





}
