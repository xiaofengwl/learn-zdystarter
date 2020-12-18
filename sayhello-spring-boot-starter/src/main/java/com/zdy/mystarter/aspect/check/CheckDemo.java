package com.zdy.mystarter.aspect.check;

import org.springframework.stereotype.Component;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/17 10:43
 * @desc
 */
//TODO 必须使用这个注解，否者：使用SpringContextHolder.getBean()无法获取对象
@Component
public class CheckDemo extends  AbsComonCheckExcutor {
    /**
     * 检查逻辑
     * @param args 请求参数
     * @return
     */
    @Override
    public boolean execute(Object[] args) {
        return super.execute(args);
    }
}
