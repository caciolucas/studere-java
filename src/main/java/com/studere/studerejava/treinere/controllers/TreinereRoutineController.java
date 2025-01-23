package com.studere.studerejava.treinere.controllers;

import com.studere.studerejava.framework.controller.PlanController;
import com.studere.studerejava.treinere.models.Exercise;
import com.studere.studerejava.treinere.models.TreinereRoutine;
import com.studere.studerejava.treinere.services.TreinereRoutineService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/routines")
public class TreinereRoutineController extends PlanController<TreinereRoutine, Exercise> {
    public TreinereRoutineController(TreinereRoutineService treinereRoutineService) {
        super(treinereRoutineService);
    }
}