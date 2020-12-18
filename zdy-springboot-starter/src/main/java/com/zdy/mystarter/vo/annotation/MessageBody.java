package com.zdy.mystarter.vo.annotation;

import com.zdy.mystarter.annotations.MessageField;
import com.zdy.mystarter.model.basic.TeacherVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * TODO ***实体
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/1/17 15:14
 * @desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageBody {

    /**
     * 私有属性-private
     */
    @MessageField(jsonField = "bodyId",xmlField = "bodyId")
    private String bodyId;

    @MessageField(jsonField = "bodyName",xmlField = "bodyName")
    private String bodyName;

    @MessageField(jsonField = "teacher",xmlField = "teacher",isReference=true)
    private TeacherVo teacher;

    @MessageField(jsonField = "list",xmlField = "list",isCollection=true)
    private List<Object> list;

    @MessageField(jsonField = "teacherList",xmlField = "teacherList",isCollection=true,isObjCollection=true,type = TeacherVo.class)
    private List<TeacherVo> teachLst;


}
