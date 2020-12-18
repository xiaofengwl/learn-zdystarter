package com.zdy.mystarter.sensitive.context;

import com.zdy.mystarter.sensitive.IContext;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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
public class SensitiveContext implements IContext {

    private Object currentObject;
    private Field currentField;
    private List<Field> allFieldList =new ArrayList<>();

    public SensitiveContext(){}

    @Override
    public Object getCurrentObject() {
        return currentObject;
    }

    public void setCurrentObject(Object currentObject) {
        this.currentObject = currentObject;
    }

    @Override
    public Field getCurrentField() {
        return currentField;
    }

    @Override
    public String getCurrentFiledName() {
        return this.currentField.getName();
    }

    @Override
    public Object getCurrentFieldValue() throws Exception{
        try{
            return this.currentField.get(this.currentObject);
        }catch (Exception e){
            throw e;
        }
    }

    public void setCurrentField(Field currentField) {
        this.currentField = currentField;
    }

    @Override
    public List<Field> getAllFieldList() {
        return allFieldList;
    }

    public void setAllFieldList(List<Field> allFieldList) {
        this.allFieldList = allFieldList;
    }
}
