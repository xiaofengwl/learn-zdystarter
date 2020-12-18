package com.zdy.mystarter.sensitive;

import java.lang.reflect.Field;
import java.util.List;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/21 13:53
 * @desc
 */
public interface IContext {

    List<Field> getAllFieldList();

    Field getCurrentField();

    String getCurrentFiledName();

    Object getCurrentFieldValue()throws Exception;

    Object getCurrentObject();
}
