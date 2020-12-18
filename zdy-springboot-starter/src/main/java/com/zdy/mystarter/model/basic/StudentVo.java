package com.zdy.mystarter.model.basic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;


/**
 * Created by Jason on 2020/1/13.
 */
@Data
public class StudentVo implements Serializable{

    /**
     * @JSONField(ordinal = 该属性在改对象中的顺序，toString()的时候会用到)
     */
    @JSONField(ordinal = 1)
    private String stId;

    @JSONField(ordinal = 2)
    private String stName;

    @JSONField(ordinal = 3)
    private String stAge;

    @JSONField(ordinal = 4)
    private String stRen;

    @JSONField(ordinal = 5)
    private String stOthers;

    @JSONField(ordinal = 6)
    private String stEmail;

    @JSONField(ordinal = 7)
    private String stAddress;

    @JSONField(ordinal = 8)
    private String stSex;

    @JSONField(ordinal = 9)
    private Integer stTeacherId;

    @Override
    public String toString(){
        return JSON.toJSONString(this);
    }
}
