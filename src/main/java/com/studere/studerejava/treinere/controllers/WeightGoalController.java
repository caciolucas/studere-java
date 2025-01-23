package com.studere.studerejava.treinere.controllers;

import com.studere.studerejava.framework.controller.GoalController;
import com.studere.studerejava.framework.services.GoalService;
import com.studere.studerejava.treinere.models.WeightGoal;
import com.studere.studerejava.treinere.models.dto.WeightGoalCreateOrUpdateDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weight-goals")
public class WeightGoalController extends GoalController<WeightGoal, WeightGoalCreateOrUpdateDTO> {
    public WeightGoalController(GoalService<WeightGoal, WeightGoalCreateOrUpdateDTO> goalService) {
        super(goalService);
    }
}
