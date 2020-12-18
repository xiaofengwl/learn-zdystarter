package com.zdy.mystarter.model.basic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * TODO RoleVo
 * <pre>
 *     实体类和DB表的关联配置
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/1/16 16:34
 * @desc
 */

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name="role")
public class RoleVo implements Serializable {   //一定要实现Serializable接口，否则会报错

    @Id
    @Column(name="id",unique = true,nullable = false)
    private Integer roleId;

    @Id
    @Column(name="roleName",unique = true,nullable = false)
    private Integer roleName;

    @Id
    @Column(name="note",unique = true,nullable = false)
    private Integer roleNote;

    public RoleVo(Integer roleId){
        this.roleId=roleId;
    }

}
