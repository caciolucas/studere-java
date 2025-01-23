package com.studere.studerejava.studere.services;

import com.studere.studerejava.framework.models.dto.response.GenericMetricResponseDTO;
import com.studere.studerejava.framework.models.dto.response.SessionStreakDTO;
import com.studere.studerejava.framework.models.enums.SessionStatus;
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
     * Fetch session streaks.
     *
     * @return List of SessionStreakDTO containing streak data.
     */
    @Override
    public List<SessionStreakDTO> getStreaks() {
        // 1. Fetch sessions for the last 7 days
        List<StudySession> sessions = sessionService.getSessionsForLastNDays(DAYS);

        // 2. Let the base class build the streaks
        return buildStreaks(sessions);
    }

    @Override
    protected boolean isDayCompleted(List<StudySession> dailySessions) {
        // A day is completed if ANY session has status == COMPLETED
        return dailySessions.stream()
                .anyMatch(s -> s.getStatus() == SessionStatus.COMPLETED);
    }

    @Override
    public List<GenericMetricResponseDTO> getAdditionalMetrics() {
        // Example: no extra metrics for Studere
        return List.of();
    }


}
