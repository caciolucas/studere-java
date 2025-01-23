package com.studere.studerejava.culinare.services;

import com.studere.studerejava.culinare.models.CookingSession;
import com.studere.studerejava.framework.models.dto.response.GenericMetricResponseDTO;
import com.studere.studerejava.framework.models.dto.response.SessionStreakDTO;
import com.studere.studerejava.framework.models.enums.SessionStatus;
import com.studere.studerejava.framework.services.DashboardService;
import com.studere.studerejava.framework.services.SessionService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CulinaryDashboardService extends DashboardService<CookingSession> {
    private static final int DAYS = 7;  // For example, fetch the last 7 days

    public CulinaryDashboardService(SessionService<CookingSession> sessionService) {
        super(sessionService);
    }

    /**
     * Decide if a day is completed. Example:
     * A day is "completed" if any CookingSession that day has status == COMPLETED.
     */
    @Override
    protected boolean isDayCompleted(List<CookingSession> dailySessions) {
        return dailySessions.stream()
                .anyMatch(s -> s.getStatus() == SessionStatus.COMPLETED);
    }

    /**
     * Fetch session streaks by:
     * 1. Getting the relevant sessions (e.g., last 7 days).
     * 2. Calling buildStreaks(...) from the parent to group & create SessionStreakDTOs.
     */
    @Override
    public List<SessionStreakDTO> getStreaks() {
        List<CookingSession> sessions = sessionService.getSessionsForLastNDays(DAYS);
        return buildStreaks(sessions);
    }

    /**
     * Return additional metrics. Example: "average cooking time (ms) over the last N days."
     */
    @Override
    public List<GenericMetricResponseDTO> getAdditionalMetrics() {
        List<CookingSession> sessions = sessionService.getSessionsForLastNDays(DAYS);

        // Compute average totalActiveTime among all sessions
        double averageCookingTime = 0.0;
        if (!sessions.isEmpty()) {
            long totalTime = sessions.stream()
                    .filter(s -> s.getTotalActiveTime() != null)
                    .mapToLong(CookingSession::getTotalActiveTime)
                    .sum();
            averageCookingTime = (double) totalTime / sessions.size();
        }

        // Build the metric data map
        Map<String, Object> data = new HashMap<>();
        data.put("days", DAYS);
        data.put("averageCookingTime", averageCookingTime);

        // Create a single metric
        GenericMetricResponseDTO metric = new GenericMetricResponseDTO("averageCookingTime", data);

        // Return one or more metrics in a list
        return List.of(metric);
    }
}
