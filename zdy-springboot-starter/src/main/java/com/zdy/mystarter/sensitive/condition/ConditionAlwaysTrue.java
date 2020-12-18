package com.zdy.mystarter.sensitive.condition;

import com.zdy.mystarter.sensitive.ICondition;
import com.zdy.mystarter.sensitive.IContext;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/21 13:56
 * @desc
 */
public class ConditionAlwaysTrue implements ICondition{

    public ConditionAlwaysTrue(){}

    /**
     *
     * @param contex
     * @return
     */
    public boolean valid(IContext contex){
        return true;
    }
}
