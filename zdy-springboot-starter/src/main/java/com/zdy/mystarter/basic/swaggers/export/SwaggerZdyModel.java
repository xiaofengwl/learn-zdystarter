package com.zdy.mystarter.basic.swaggers.export;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO 定制发布接口·SwaggerZdyModel
 * <pre>
 *     发布model
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/23 16:13
 * @desc
 */
public class SwaggerZdyModel {
    /**
     *
     */
    String name;
    /**
     *
     */
    String id;
    /**
     * 请求URI路径
     */
    String path;
    /**
     * 请求参数
     */
    List<SwaggerParameterModel> params = new ArrayList<>();
    /**
     * 应答参数
     */
    List<SwaggerParameterModel> returnType = new ArrayList<>();

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SwaggerParameterModel> getReturnType() {
        return returnType;
    }

    public void setReturnType(List<SwaggerParameterModel> returnType) {
        this.returnType = returnType;
    }

    public List<SwaggerParameterModel> getParams() {
        return params;
    }

    public void setParams(List<SwaggerParameterModel> params) {
        this.params = params;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
