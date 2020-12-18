package com.zdy.mystarter.lambda;

import com.zdy.mystarter.lambda.vo.Item;

/**
 * Created by Jason on 2020/1/15.
 */
public interface ItemCreatorParamContruct {

    Item getItem(int id, String name, double price);
}


