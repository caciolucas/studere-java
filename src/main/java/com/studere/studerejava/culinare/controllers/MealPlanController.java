package com.studere.studerejava.culinare.controllers;

import com.studere.studerejava.culinare.models.Meal;
import com.studere.studerejava.culinare.models.MealPlan;
import com.studere.studerejava.framework.controller.PlanController;
import com.studere.studerejava.framework.services.PlanService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/meal-plans")
public class MealPlanController extends PlanController<MealPlan, Meal> {
    public MealPlanController(PlanService<MealPlan, Meal> planService) {
        super(planService);
    }
}
