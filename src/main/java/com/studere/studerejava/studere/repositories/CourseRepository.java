package com.studere.studerejava.studere.repositories;

import com.studere.studerejava.framework.repositories.ModuleRepository;
import com.studere.studerejava.studere.models.Course;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends ModuleRepository<Course> {
}
