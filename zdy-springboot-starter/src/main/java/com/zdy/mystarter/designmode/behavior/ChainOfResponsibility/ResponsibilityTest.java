package com.zdy.mystarter.designmode.behavior.ChainOfResponsibility;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/11/13 10:57
 * @desc
 */
public class ResponsibilityTest {

    /**
     * 测试入口
     * @param args
     */
    public static void main(String[] args) {
        //请假条
        LeaveRequest request = LeaveRequest.builder().leaveDays(20).name("小明").build();

        //审批流程

        //创建审批节点
        AbstractLeaveHandler directLeaderLeaveHandler = new DirectLeaderLeaveHandler("县令");
        DeptManagerLeaveHandler deptManagerLeaveHandler = new DeptManagerLeaveHandler("知府");
        GManagerLeaveHandler gManagerLeaveHandler = new GManagerLeaveHandler("京兆尹");

        //组装审批节点
        directLeaderLeaveHandler.setNextHandler(deptManagerLeaveHandler);
        deptManagerLeaveHandler.setNextHandler(gManagerLeaveHandler);
        directLeaderLeaveHandler.handlerRequest(request);


    }
}
