package com.zdy.mystarter.sensitive.strategory;

import com.zdy.mystarter.sensitive.IContext;
import com.zdy.mystarter.sensitive.IStrategy;
import com.zdy.mystarter.sensitive.util.StrUtil;

/**
 * TODO 电话号码脱敏处理逻辑
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/21 14:42
 * @desc
 */
public class StrategyTelephoneNuber implements IStrategy{

    public StrategyTelephoneNuber(){}

    @Override
    public Object des(Object original, IContext var2) {
        String middle="***";

        return StrUtil.buildString(original,middle,3);
    }
}
