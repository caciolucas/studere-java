package com.studere.studerejava.studere.controllers;

import com.studere.studerejava.framework.controller.PlanController;
import com.studere.studerejava.studere.models.StudyPlan;
import com.studere.studerejava.studere.models.StudyPlanTopic;
import com.studere.studerejava.studere.services.StudyPlanService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/study-plans")
public class StudyPlanController extends PlanController<StudyPlan, StudyPlanTopic> {
    public StudyPlanController(StudyPlanService planService) {
        super(planService);
    }
}
