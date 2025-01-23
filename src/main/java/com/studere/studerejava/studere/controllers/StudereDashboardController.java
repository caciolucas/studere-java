package com.studere.studerejava.studere.controllers;

import com.studere.studerejava.framework.controller.DashboardController;
import com.studere.studerejava.framework.services.DashboardService;
import com.studere.studerejava.studere.models.StudySession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class StudereDashboardController extends DashboardController<StudySession> {
    public StudereDashboardController(DashboardService<StudySession> dashboardService) {
        super(dashboardService);
    }
}
