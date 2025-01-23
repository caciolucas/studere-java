package com.studere.studerejava.studere.services;

import com.studere.studerejava.framework.models.dto.response.GenericMetricResponseDTO;
import com.studere.studerejava.framework.models.dto.response.SessionStreakDTO;
import com.studere.studerejava.framework.models.enums.SessionStatus;
import com.studere.studerejava.framework.services.DashboardService;
import com.studere.studerejava.studere.models.StudySession;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        List<StudySession> sessions = sessionService.getSessionsForLastNDays(DAYS);

        // 1. Group sessions by their creation date (just the date portion, no time).
        Map<LocalDate, List<StudySession>> sessionsByDate = sessions.stream()
                .collect(Collectors.groupingBy(s -> {
                    // Convert the Date to LocalDate
                    return s.getCreatedAt().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                }));

        // 2. Build a streak DTO for each date group
        List<SessionStreakDTO> streaks = new ArrayList<>();
        for (Map.Entry<LocalDate, List<StudySession>> entry : sessionsByDate.entrySet()) {
            LocalDate date = entry.getKey();
            List<StudySession> dailySessions = entry.getValue();

            // TODO: Remove this comment, only for explanation purposes
            // Decide how you define "completed" for that day.
            // For example: the day is "completed" if *any* session is completed.
            boolean completed = dailySessions.stream()
                    .anyMatch(s -> s.getStatus() == SessionStatus.COMPLETED);

            SessionStreakDTO streakDTO = new SessionStreakDTO();

            streakDTO.setReferenceDate(
                    Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())
            );
            streakDTO.setCompleted(completed);

            streaks.add(streakDTO);
        }

        return streaks;
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
