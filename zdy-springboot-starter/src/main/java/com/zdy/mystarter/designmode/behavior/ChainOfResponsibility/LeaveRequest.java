package com.zdy.mystarter.designmode.behavior.ChainOfResponsibility;

import lombok.*;

/**
 * TODO 员工提交请求类
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/11/13 10:52
 * @desc
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveRequest {
    /**天数*/
    private int leaveDays;

    /**姓名*/
    private String name;
}

