package com.studere.studerejava.framework.services;

import com.studere.studerejava.framework.models.Module;
import com.studere.studerejava.framework.models.Session;
import com.studere.studerejava.framework.models.dto.response.GenericMetricResponseDTO;
import com.studere.studerejava.framework.models.dto.response.SessionStreakDTO;
import com.studere.studerejava.framework.models.dto.response.SessionTimeDistributionDTO;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

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
    public List<SessionTimeDistributionDTO> getTimeDistribution(UUID userId) {
        // 1. Fetch the relevant sessions
        //    Depending on your use-case, this might be "all" sessions or the last N days, etc.
        List<T> sessions = sessionService.findByPlanUserId(userId);
        // Or: sessionService.getSessionsForLastNDays(DAYS);

        // 2. Group by Module and sum totalActiveTime
        Map<Module, Long> totalTimeByModule = sessions.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getPlan().getModule(),
                        Collectors.summingLong(
                                s -> s.getTotalActiveTime() == null ? 0L : s.getTotalActiveTime()
                        )
                ));

        // 3. Transform each map entry into SessionTimeDistributionDTO
        return totalTimeByModule.entrySet().stream()
                .map(entry -> {
                    SessionTimeDistributionDTO dto = new SessionTimeDistributionDTO();
                    dto.setModule(entry.getKey());
                    dto.setTotalDuration(entry.getValue());   // the total active time sum
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Groups sessions by the LocalDate portion of their createdAt.
     */
    protected Map<LocalDate, List<T>> groupSessionsByDate(List<T> sessions) {
        return sessions.stream()
                .collect(Collectors.groupingBy(session ->
                        session.getCreatedAt()
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                ));
    }

    /**
     * Decides if a group of sessions for a specific day is considered "completed".
     * Subclasses must define their own rule.
     */
    protected abstract boolean isDayCompleted(List<T> dailySessions);

    /**
     * Common method to build the streak DTOs from a list of sessions.
     * 1. Group by date
     * 2. For each date, build a SessionStreakDTO with "completed" decided by isDayCompleted(...)
     * <p>
     * Works like a template method by calling isDayCompleted(...) which must be implemented by subclasses.
     */
    protected List<SessionStreakDTO> buildStreaks(List<T> sessions) {
        Map<LocalDate, List<T>> sessionsByDate = groupSessionsByDate(sessions);

        List<SessionStreakDTO> streaks = new ArrayList<>();

        for (Map.Entry<LocalDate, List<T>> entry : sessionsByDate.entrySet()) {
            LocalDate date = entry.getKey();
            List<T> dailySessions = entry.getValue();

            SessionStreakDTO streakDTO = new SessionStreakDTO();
            streakDTO.setReferenceDate(
                    Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())
            );

            // Use the subclass rule:
            streakDTO.setCompleted(isDayCompleted(dailySessions));

            streaks.add(streakDTO);
        }

        return streaks;
    }

    /**
     * Fetch session streaks. Each subclass can retrieve its sessions and then
     * call buildStreaks(...) to handle the grouping + completed logic.
     */
    public abstract List<SessionStreakDTO> getStreaks();

    /**
     * Fetch generic metrics. Each implementation can add custom metrics.
     *
     * @return List of MetricResponseDTO containing key-value metric data.
     */
    public abstract List<GenericMetricResponseDTO> getAdditionalMetrics();
}
