package com.zdy.mystarter.basic.swaggers.export;

/**
 * TODO 定制发布接口·SwaggerParameterModel
 * <pre>
 *     一个参数基础类
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/23 16:12
 * @desc
 */
public class SwaggerParameterModel {

    String key;
    String example = "";
    String type = "string";
    String description="";
    Object reference;

    public Object getReference() {
        return reference;
    }

    public void setReference(Object reference) {
        this.reference = reference;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public SwaggerParameterModel(String key,String description) {
        this.key = key;
        this.description = description==null?"":description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
