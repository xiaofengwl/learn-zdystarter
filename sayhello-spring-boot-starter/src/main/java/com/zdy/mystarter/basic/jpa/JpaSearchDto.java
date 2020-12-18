package com.zdy.mystarter.basic.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * TODO JPA搜索定义
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/1/16 10:51
 * @desc
 */
@Data                               //自动装配getter、setter、toString等方法
@Accessors(chain = true)            //setter返回类型不再是void，而是当前实体类型
@NoArgsConstructor                  //生成一个无参数的构造方法
@AllArgsConstructor                 //会生成一个包含所有变量，同时如果变量使用了NotNull annotation ， 会进行是否为空的校验
public class JpaSearchDto {

    /**
     * 搜索字段
     */
    private String fieldName;

    /**
     * 搜索类型{@link com.zdy.mystarter.basic.jpa.DBConstants}
     */
    private String type;

    /**
     * 搜索对应值
     */
    private Object value;

    /**
     * 开始值
     */
    private Object start;

    /**
     * 结束值
     */
    private Object end;

    /**
     * substr
     */
    private Integer from;

    /**
     * substr
     */
    private Object len;

    /**
     * or多条件搜索
     */
    private List<JpaSearchDto> orCondition;

}
