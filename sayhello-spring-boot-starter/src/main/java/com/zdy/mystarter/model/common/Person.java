package com.zdy.mystarter.model.common;

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
 * @date 2020/2/26 18:32
 * @desc
 */
@Data
public class Person{
    private String name;
    private String address;
    private List<Person> personList;
}
