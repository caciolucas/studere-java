package com.studere.studerejava.framework.controller;

import com.studere.studerejava.framework.core.exceptions.BaseException;
import com.studere.studerejava.framework.models.Session;
import com.studere.studerejava.framework.models.dto.response.ErrorResponseDTO;
import com.studere.studerejava.framework.services.DashboardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.UUID;

public abstract class DashboardController<T extends Session> {
    private final DashboardService<T> dashboardService;

    public DashboardController(DashboardService<T> dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/streaks")
    public ResponseEntity<?> getStreaksMetric(Principal principal) {
        try {
            return ResponseEntity.ok(dashboardService.getStreaks());
        } catch (BaseException e) {
            throw e;
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }


    @GetMapping("/time-distribution")
    public ResponseEntity<?> getTimeDistributionMetric(Principal principal) {
        try {
            return ResponseEntity.ok(dashboardService.getTimeDistribution(UUID.fromString(principal.getName())));
        } catch (BaseException e) {
            throw e;
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/other-metrics")
    public ResponseEntity<?> getAdditionalMetrics(Principal principal) {
        try {
            return ResponseEntity.ok(dashboardService.getAdditionalMetrics());
        } catch (BaseException e) {
            throw e;
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
