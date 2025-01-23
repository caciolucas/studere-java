package com.studere.studerejava.treinere.services;

import com.studere.studerejava.framework.models.dto.response.GenericMetricResponseDTO;
import com.studere.studerejava.framework.models.dto.response.SessionStreakDTO;
import com.studere.studerejava.framework.models.enums.SessionStatus;
import com.studere.studerejava.framework.services.DashboardService;
import com.studere.studerejava.framework.services.SessionService;
import com.studere.studerejava.treinere.models.TreinereSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TreinereDashboardService extends DashboardService<TreinereSession> {
    private static final int DAYS = 30;

    public TreinereDashboardService(SessionService<TreinereSession> sessionService) {
        super(sessionService);
    }

    @Override
    public List<SessionStreakDTO> getStreaks() {
        // 1. Fetch sessions for the last 30 days
        List<TreinereSession> sessions = sessionService.getSessionsForLastNDays(DAYS);

        // 2. Call the shared buildStreaks(...) logic
        return buildStreaks(sessions);
    }

    @Override
    protected boolean isDayCompleted(List<TreinereSession> dailySessions) {
        // For Treinere, if there is at least one session, that day is completed
        return !dailySessions.isEmpty();
    }

    /**
     * Two example metrics:
     * - "average_session_duration": average 'totalActiveTime' in last N days
     * - "session_completion_rate": ratio of COMPLETED sessions to total sessions
     */
    @Override
    public List<GenericMetricResponseDTO> getAdditionalMetrics() {
        List<TreinereSession> sessions = sessionService.getSessionsForLastNDays(DAYS);

        // 1. Average Session Duration
        double averageDuration = 0.0;
        if (!sessions.isEmpty()) {
            long totalDuration = sessions.stream()
                    .filter(s -> s.getTotalActiveTime() != null)
                    .mapToLong(TreinereSession::getTotalActiveTime)
                    .sum();

            averageDuration = (double) totalDuration / sessions.size();
        }

        Map<String, Object> avgDurationData = new HashMap<>();
        avgDurationData.put("days", DAYS);
        avgDurationData.put("averageDuration", averageDuration);

        GenericMetricResponseDTO avgDurationMetric = new GenericMetricResponseDTO(
                "average_session_duration",
                avgDurationData
        );

        // 2. Session Completion Rate
        long completedCount = sessions.stream()
                .filter(s -> s.getStatus() == SessionStatus.COMPLETED)
                .count();

        double completionRate = (sessions.isEmpty())
                ? 0.0
                : (completedCount / (double) sessions.size()) * 100.0;

        Map<String, Object> completionData = new HashMap<>();
        completionData.put("days", DAYS);
        completionData.put("completedCount", completedCount);
        completionData.put("totalSessions", sessions.size());
        completionData.put("completionRatePercent", completionRate);

        GenericMetricResponseDTO completionMetric = new GenericMetricResponseDTO(
                "session_completion_rate",
                completionData
        );

        // Return both metrics
        return List.of(avgDurationMetric, completionMetric);
    }
}
