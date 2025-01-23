package com.studere.studerejava.treinere.controllers;

import com.studere.studerejava.framework.controller.DashboardController;
import com.studere.studerejava.framework.services.DashboardService;
import com.studere.studerejava.treinere.models.TreinereSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class TreinereDashboardController extends DashboardController<TreinereSession> {
    public TreinereDashboardController(DashboardService<TreinereSession> dashboardService) {
        super(dashboardService);
    }
}
