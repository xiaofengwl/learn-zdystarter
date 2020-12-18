package com.zdy.mystarter.vo;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/23 11:27
 * @desc
 */

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ResponseHolderData {
    /**
     * 响应码值
     */
    private Integer status;
    /**
     * 响应码说明
     */
    private String message;

    /**
     * 按照指定类型做返回
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getDefaultResponseData(Class<T> clazz){
        T retObj=null;
        if(clazz.isAssignableFrom(Map.class)){
            retObj=(T)getDefaultMapResponseData();
        }
        return retObj;
    }

    /**
     * 返回默认Map
     * @return
     */
    private static Map getDefaultMapResponseData(){
        Map map=new HashMap();
        map.put("status",2001);
        map.put("message","success");
        return map;
    }

}
