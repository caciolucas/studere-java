package com.studere.studerejava.studere.controllers;

import com.studere.studerejava.framework.controller.ModuleController;
import com.studere.studerejava.studere.models.Course;
import com.studere.studerejava.studere.services.CourseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
public class CourseController extends ModuleController<Course> {
    public CourseController(CourseService courseService) {
        super(courseService);
    }
}
