package com.zdy.mystarter.lambda.vo;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * Created by Jason on 2020/1/15.
 */
@Data
public class Item {

    private int id;

    private String name;

    private double price;

    public Item(){}

    public Item(int id,String name,double price){
        this.id=id;
        this.name=name;
        this.price=price;
    }

    @Override
    public String toString(){
        return JSON.toJSONString(this);
    }
}
