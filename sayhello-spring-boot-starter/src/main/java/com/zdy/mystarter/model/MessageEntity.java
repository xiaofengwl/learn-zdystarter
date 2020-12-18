package com.zdy.mystarter.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/2/23 19:08
 * @desc
 */
@Data
public class MessageEntity {

    private String a;

    private String b;

    private String c;

    private String d;

    public MessageEntity(String a,String b,String c,String d){
        this.a=a;
        this.b=b;
        this.c=c;
        this.d=d;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
