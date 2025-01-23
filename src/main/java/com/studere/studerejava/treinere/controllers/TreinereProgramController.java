package com.studere.studerejava.treinere.controllers;

import com.studere.studerejava.framework.controller.ModuleController;
import com.studere.studerejava.treinere.models.TreinereProgram;
import com.studere.studerejava.treinere.services.TreinereProgramService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/programs")
public class TreinereProgramController extends ModuleController<TreinereProgram> {
    public TreinereProgramController(TreinereProgramService treinereProgramService) {
        super(treinereProgramService);
    }
}
