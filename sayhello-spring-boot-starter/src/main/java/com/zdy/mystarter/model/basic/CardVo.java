package com.zdy.mystarter.model.basic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 *
 * Created by Jason on 2020/1/13.
 */
@Data
public class CardVo {

    @JSONField(ordinal = 1)
    private String cdNo;

    @JSONField(ordinal = 2)
    private Integer cdTeacherId;

    @JSONField(ordinal = 3)
    private String cdDate;

    @JSONField(ordinal = 4)
    private String cdRen;

    @Override
    public String toString(){
        return JSON.toJSONString(this);
    }

}
