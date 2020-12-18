package com.zdy.mystarter.model.basic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by Jason on 2020/1/14.
 */
@Data
public class CourseVo {

    @JSONField(ordinal = 1)
    private Integer cId;

    @JSONField(ordinal = 2)
    private String  cName;

    @JSONField(ordinal = 3)
    private String cAddress;

    @JSONField(ordinal = 4)
    private String cRen;

    @Override
    public String toString(){
        return JSON.toJSONString(this);
    }
}
