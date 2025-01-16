package com.studere.studerejava.framework.services;

import com.studere.studerejava.framework.models.Session;
import com.studere.studerejava.framework.models.dto.response.GenericMetricResponseDTO;
import com.studere.studerejava.framework.models.dto.response.SessionStreakDTO;
import com.studere.studerejava.framework.models.dto.response.SessionTimeDistributionDTO;

import java.util.List;

public abstract class DashboardService<T extends Session> {
    protected SessionService<T> sessionService;

    public DashboardService(SessionService<T> sessionService) {
        this.sessionService = sessionService;
    }

    /**
     * Fetch session time distribution.
     *
     * @return List of SessionTimeDistributionDTO containing time distribution data.
     */
    public abstract List<SessionTimeDistributionDTO> getTimeDistribution();

    /**
     * Fetch session streaks.
     *
     * @return List of SessionStreakDTO containing streak data.
     */
    public abstract List<SessionStreakDTO> getStreaks();

    /**
     * Fetch generic metrics. Each implementation can add custom metrics.
     *
     * @return List of MetricResponseDTO containing key-value metric data.
     */
    public abstract List<GenericMetricResponseDTO> getAdditionalMetrics();
}
