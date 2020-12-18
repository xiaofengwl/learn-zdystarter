package com.zdy.mystarter.model.basic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.zdy.mystarter.annotations.MessageField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jason on 2020/1/14.
 */

/**
 * TODO @Accessors 因此有3个选择：
 *   1. fluent 一个布尔值。如果为真，pepper的getter就是 pepper()，setter方法就是pepper(T newValue)。并且，除非特别说明，chain默认为真。
 *   2. chain 一个布尔值。如果为真，产生的setter返回的this而不是void。默认是假。如果fluent=true，那么chain默认为真。
 * TODO 影响：
 *   1.chain=true会影响toString()打印信息。
 *   2.chain和fluent会影响该实体类的setter注入方式。
 */
@Data
@Accessors(chain=true)
public class TeacherVo implements Serializable {

    @MessageField(jsonField = "tId" ,xmlField = "tId")
    @JSONField(ordinal = 1)
    private Integer tId;

    @MessageField(jsonField = "tName" ,xmlField = "tName")
    @JSONField(ordinal = 2)
    private String tName;

    @MessageField(jsonField = "tSex" ,xmlField = "tSex")
    @JSONField(ordinal = 3)
    private String tSex;

    @MessageField(jsonField = "tAddress" ,xmlField = "tAddress")
    @JSONField(ordinal = 4)
    private String tAddress;

    @MessageField(jsonField = "tCourseId" ,xmlField = "tCourseId")
    @JSONField(ordinal = 5)
    private Integer tCourseId;
    /**
     * 一对一的关系
     */
    @JSONField(ordinal = 6)
    private CourseVo course;
    /**
     * 一对多的关系
     */
    @JSONField(ordinal = 7)
    private List<StudentVo> students;

    @Override
    public String toString(){
        return JSON.toJSONString(this);
    }

}
