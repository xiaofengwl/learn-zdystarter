package com.zdy.mystarter.service.common.impl;

import com.zdy.mystarter.intergration.mapper.basic.BasicMapper;
import com.zdy.mystarter.model.basic.CourseVo;
import com.zdy.mystarter.model.basic.StudentVo;
import com.zdy.mystarter.model.basic.TeacherVo;
import com.zdy.mystarter.service.basic.IBasicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Jason on 2020/1/14.
 */
@Service
public class BasicService implements IBasicService{

    /**
     * 装配mapper--测试联合查询
     */
    @Resource
    BasicMapper basicMapper;

    /**
     * 新增Tacher数据
     *
     * @param teacherVo
     * @return
     */
    @Override
    public boolean insertOneTeacherInfo(TeacherVo teacherVo) {
        return basicMapper.insertOneTeacherInfo(teacherVo)>=1?true:false;
    }

    /**
     * 新增Courrse数据
     *
     * @param courseVo
     * @return
     */
    @Override
    public boolean insertOneCourseInfo(CourseVo courseVo) {
        return basicMapper.insertOneCourseInfo(courseVo)>=1?true:false;
    }

    /**
     * 新增Student数据
     *
     * @param stuList
     * @return
     */
    @Override
    public boolean insertMoreStudentInfo(List<StudentVo> stuList) {
        return basicMapper.insertMoreStudentInfo(stuList)>=1?true:false;
    }
}
