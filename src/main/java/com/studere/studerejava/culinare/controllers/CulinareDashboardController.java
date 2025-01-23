package com.studere.studerejava.culinare.controllers;

import com.studere.studerejava.culinare.models.CookingSession;
import com.studere.studerejava.framework.controller.DashboardController;
import com.studere.studerejava.framework.services.DashboardService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class CulinareDashboardController extends DashboardController<CookingSession> {
    public CulinareDashboardController(DashboardService<CookingSession> dashboardService) {
        super(dashboardService);
    }
}
