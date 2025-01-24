package com.studere.studerejava.studere.controllers;

import com.studere.studerejava.framework.controller.GoalController;
import com.studere.studerejava.framework.services.GoalService;
import com.studere.studerejava.studere.models.Activity;
import com.studere.studerejava.studere.models.dto.ActivityCreateOrUpdateDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/activities")
public class ActivityController extends GoalController<Activity, ActivityCreateOrUpdateDTO> {

    public ActivityController(GoalService<Activity, ActivityCreateOrUpdateDTO> goalService) {
        super(goalService);
    }
}
