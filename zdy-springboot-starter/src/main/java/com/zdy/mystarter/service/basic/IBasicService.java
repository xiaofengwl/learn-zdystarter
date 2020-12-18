package com.zdy.mystarter.service.basic;

import com.zdy.mystarter.model.basic.CourseVo;
import com.zdy.mystarter.model.basic.StudentVo;
import com.zdy.mystarter.model.basic.TeacherVo;

import java.util.List;

/**
 * 基础服务接口
 * Created by Jason on 2020/1/14.
 */
public interface IBasicService {

    /**
     * 新增Tacher数据
     * @param teacherVo
     * @return
     */
     boolean insertOneTeacherInfo(TeacherVo teacherVo);

    /**
     * 新增Courrse数据
     * @param courseVo
     * @return
     */
     boolean insertOneCourseInfo(CourseVo courseVo);

    /**
     * 新增Student数据
      * @param stuList
     * @return
     */
     boolean insertMoreStudentInfo(List<StudentVo> stuList);


}
