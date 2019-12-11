package com.lyx.las.controller;

import com.lyx.las.dao.CourseMapper;
import com.lyx.las.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseMapper courseMapper;

    @GetMapping
    public ResponseEntity<List<Course>> getCourses() {
        List<Course> courses = courseMapper.getAllCourses();
        return ResponseEntity.ok().body(courses);
    }
}
