package com.studere.studerejava.studere.services;

import com.studere.studerejava.framework.models.dto.response.GenericMetricResponseDTO;
import com.studere.studerejava.framework.models.dto.response.SessionStreakDTO;
import com.studere.studerejava.framework.models.dto.response.SessionTimeDistributionDTO;
import com.studere.studerejava.framework.services.DashboardService;
import com.studere.studerejava.studere.models.StudySession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudereDashboardService extends DashboardService<StudySession> {
    // Number of days to fetch session data for.
    private static final int DAYS = 7;

    public StudereDashboardService(StudySessionService sessionService) {
        super(sessionService);
    }

    /**
     * Fetch session time distribution.
     *
     * @return List of SessionTimeDistributionDTO containing time distribution data.
     */
    @Override
    public List<SessionTimeDistributionDTO> getTimeDistribution() {
        return List.of();
    }

    /**
     * Fetch session streaks.
     *
     * @return List of SessionStreakDTO containing streak data.
     */
    @Override
    public List<SessionStreakDTO> getStreaks() {
        List<StudySession> sessions = sessionService.getSessionsForLastNDays(DAYS);
        // Group the sessions by createdAt date and return the streaks.
        // TODO: Implement this method.
        return List.of();
    }

    /**
     * Fetch generic metrics. Each implementation can add custom metrics.
     *
     * @return List of MetricResponseDTO containing key-value metric data.
     */
    @Override
    public List<GenericMetricResponseDTO> getAdditionalMetrics() {
        return List.of();
    }
}
