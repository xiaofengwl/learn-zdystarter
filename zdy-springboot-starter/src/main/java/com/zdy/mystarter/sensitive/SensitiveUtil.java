package com.zdy.mystarter.sensitive;

import com.zdy.mystarter.sensitive.service.SensitiveService;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/21 13:51
 * @desc
 */
public final class SensitiveUtil {

    public SensitiveUtil(){}

    public static <T> T desCopy(T object)throws Exception{
        ISensitive sensitive=new SensitiveService();
        return (T)sensitive.desCopy(object);
    }

}
