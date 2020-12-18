package com.zdy.mystarter.vo.redis;

import lombok.Data;

import java.util.List;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/10/22 14:21
 * @desc
 */
@Data
public class RedisInputVO{

    /**
     * todo 操作类型：码值如下
     *      0-写入字符串
     *      1-写入实体对象，
     *      2-写入简单数字的加减法处理
     *      3-写入集合
     *      4-写入集合，读取排序
     *      5-写入集合，做分页查询
     *      ....
     */
    private String operType;

    private String name;

    private int age;

    private char sex;

    private String address;

    private DataInfomation info;

    private List<DataInfomation> dataList;

    private String sortField;

    private long beginPos;
    private long endPos;

}
