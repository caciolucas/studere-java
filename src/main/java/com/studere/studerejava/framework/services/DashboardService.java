package com.studere.studerejava.framework.services;

import com.studere.studerejava.framework.models.Module;
import com.studere.studerejava.framework.models.Session;
import com.studere.studerejava.framework.models.dto.response.GenericMetricResponseDTO;
import com.studere.studerejava.framework.models.dto.response.SessionStreakDTO;
import com.studere.studerejava.framework.models.dto.response.SessionTimeDistributionDTO;

import java.util.List;
import java.util.Map;
import java.util.UUID;
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
