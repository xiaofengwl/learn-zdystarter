package com.zdy.mystarter.basic.swaggers.service;

import java.util.List;
import java.util.Map;

/**
 * TODO 抽象服务发布基础接口
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/23 16:39
 * @desc
 */
public interface ZDYServiceExportor {

    /**
     * 向服务中注入数据源，该数据源含义：含有要发布的接口信息的数据集合
     * @param dataList
     */
    void exportZdyService(List<Map<String, Object>> dataList);
}
