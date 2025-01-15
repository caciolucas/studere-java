package com.studere.studerejava.studere.services;

import com.studere.studerejava.framework.core.exceptions.NotFoundException;
import com.studere.studerejava.framework.repositories.ModuleRepository;
import com.studere.studerejava.framework.services.ModuleService;
import com.studere.studerejava.studere.models.Course;
import com.studere.studerejava.studere.models.Term;
import com.studere.studerejava.studere.models.dto.CourseCreateOrUpdateDTO;
import com.studere.studerejava.studere.repositories.TermRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CourseService extends ModuleService<Course> {
    private TermRepository termRepository;

    public CourseService(ModuleRepository<Course> moduleRepository, TermRepository termRepository) {
        super(moduleRepository);
        this.termRepository = termRepository;
    }

    @Override
    protected Course createNewModule() {
        return new Course();
    }

    public Course createModule(CourseCreateOrUpdateDTO courseCreateOrUpdateDTO, UUID userId) {
        Course newModule = createNewModule();

        Term term = termRepository.findByIdAndUserId(courseCreateOrUpdateDTO.getTermId(), userId)
                .orElseThrow(() -> new NotFoundException("Term not found"));

        newModule.setName(courseCreateOrUpdateDTO.getName());
        newModule.setDescription(courseCreateOrUpdateDTO.getDescription());
        newModule.setTerm(term);
        newModule.setUser(term.getUser());

        return moduleRepository.save(newModule);
    }

    public Course updateModule(CourseCreateOrUpdateDTO courseCreateOrUpdateDTO, UUID courseId, UUID userId) {
        Course course = moduleRepository.findByIdAndUserId(courseId, userId)
                .orElseThrow(() -> new NotFoundException("Course not found"));

        Term term = termRepository.findByIdAndUserId(courseCreateOrUpdateDTO.getTermId(), userId)
                .orElseThrow(() -> new NotFoundException("Term not found"));

        course.setName(courseCreateOrUpdateDTO.getName());
        course.setDescription(courseCreateOrUpdateDTO.getDescription());
        course.setTerm(term);

        return moduleRepository.save(course);
    }


}
