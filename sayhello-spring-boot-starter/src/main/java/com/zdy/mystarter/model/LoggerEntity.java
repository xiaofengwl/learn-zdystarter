package com.zdy.mystarter.model;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/2/20 9:08
 * @desc
 */
public class LoggerEntity {

    private String method;
    private String uri;
    private Object[] args;
    private Object result;
    private String operator;
    private String appName;

    /**
     * 获取当前对象
     *
     * @param method
     * @param uri
     * @param args
     * @param result
     * @return
     */
    public LoggerEntity get(String method, String uri, Object[] args, Object result, String operator, String appName) {
        setMethod(method);
        setUri(uri);
        setArgs(args);
        setResult(result);
        setOperator(operator);
        setAppName(appName);
        return this;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
