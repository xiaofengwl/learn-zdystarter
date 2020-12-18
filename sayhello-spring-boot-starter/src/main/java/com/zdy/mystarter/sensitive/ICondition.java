package com.zdy.mystarter.sensitive;

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
public interface ICondition {
    boolean valid(IContext context);
}
