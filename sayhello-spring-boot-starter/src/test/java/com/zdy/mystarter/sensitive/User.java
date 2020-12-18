package com.zdy.mystarter.sensitive;

import com.zdy.mystarter.sensitive.annotation.Sensitive;
import com.zdy.mystarter.sensitive.strategory.StrategyTelephoneNuber;
import lombok.Data;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/21 16:40
 * @desc
 */

public class User {

    @Sensitive(strategy = StrategyTelephoneNuber.class)
    private String phone;

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "phone="+phone;
    }
}
