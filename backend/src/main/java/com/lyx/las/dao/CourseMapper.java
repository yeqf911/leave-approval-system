package com.lyx.las.dao;

import com.lyx.las.model.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseMapper {

    @Select("SELECT teacher_id FROM courses WHERE id = #{id}")
    int getTeacherByCourseId(@Param("id") int id);

    @Select("SELECT * FROM courses")
    List<Course> getAllCourses();

    @Select("SELECT name FROM courses WHERE id = #{id}")
    String getCourseNameById(@Param("id") int id);
}
