package com.zdy.mystarter.sensitive;

import com.zdy.mystarter.sensitive.annotation.SensitiveEntity;
import lombok.Data;

import java.util.List;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/21 16:42
 * @desc
 */
@Data
public class UserEntity {

    @SensitiveEntity
    private User user;

    @SensitiveEntity
    private List<User> userList;

    @SensitiveEntity
    private User[] userArray;

}
