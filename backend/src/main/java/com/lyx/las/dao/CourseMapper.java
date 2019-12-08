package com.lyx.las.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CourseMapper {

    @Select("SELECT teacher_id FROM courses WHERE id = #{id}")
    int getTeacherByCourseId(@Param("id") int id);
}
