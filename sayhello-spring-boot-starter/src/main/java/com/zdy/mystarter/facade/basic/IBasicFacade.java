package com.zdy.mystarter.facade.basic;

import com.zdy.mystarter.model.basic.RoleVo;
import com.zdy.mystarter.model.basic.TeacherVo;

import java.util.List;

/**
 * Created by Jason on 2020/1/14.
 */
public interface IBasicFacade {


    /**
     * 新增Tacher数据（综合方法）
     * @param teacherVo
     * @return
     */
    boolean insertOneTeacherInfo(TeacherVo teacherVo);

    /**
     * 根据条件查询信息
     * @param roleVo
     * @return
     */
    List<RoleVo> qryAllTeachInfo(RoleVo roleVo);

}
