package com.studere.studerejava.framework.services;

import com.studere.studerejava.framework.core.exceptions.ActiveSessionExistsException;
import com.studere.studerejava.framework.core.exceptions.NotFoundException;
import com.studere.studerejava.framework.core.exceptions.PauseInactiveSessionException;
import com.studere.studerejava.framework.models.Plan;
import com.studere.studerejava.framework.models.Session;
import com.studere.studerejava.framework.models.dto.request.SessionStartDTO;
import com.studere.studerejava.framework.models.enums.SessionStatus;
import com.studere.studerejava.framework.repositories.SessionRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class SessionService<T extends Session> {
    private final SessionRepository<T> sessionRepository;
    private final PlanService planService;

    public SessionService(SessionRepository<T> sessionRepository, PlanService planService) {
        this.sessionRepository = sessionRepository;
        this.planService = planService;
    }

    protected abstract T createNewSession();

    public List<T> listSessionsFromPlan(UUID planId, UUID userId) {
        // This will throw an exception if the module does not exist or doesnt belong to the user
        Plan plan = planService.findPlanById(planId, userId);
        return sessionRepository.findByPlanId(planId);
    }

    public List<T> listAllUserSessions(UUID userId) {
        return sessionRepository.findByPlanUserId(userId);
    }

    public Optional<T> findActiveSessionByPlanId(UUID planId) {
        return sessionRepository.findActiveSessionByPlanId(planId);
    }

    public T startSession(SessionStartDTO sessionStartDTO, UUID userId) {
        Plan plan = planService.findPlanById(sessionStartDTO.getPlanId(), userId);
        Optional<T> session = findActiveSessionByPlanId(plan.getId());

        if (session.isPresent()) {
            throw new ActiveSessionExistsException("There is already an active session for this plan");
        }


        T newSession = createNewSession();
        newSession.setTitle(sessionStartDTO.getTitle());
        newSession.setDescription(sessionStartDTO.getDescription());
        newSession.setPlan(plan);

        return newSession;
    }

    public T pauseSession(UUID sessionId, UUID userId) {
        T session = sessionRepository.findByIdAndPlanUserId(sessionId, userId).orElseThrow(
                () -> new NotFoundException("Session not found")
        );

        if (session.getStatus() != SessionStatus.ACTIVE) {
            throw new PauseInactiveSessionException("Session is not active");
        }
        session.setStatus(SessionStatus.PAUSED);
        session.setLastPausedAt(new Date());
        return sessionRepository.save(session);
    }

    public T resumeSession(UUID sessionId, UUID userId) {
        T session = sessionRepository.findByIdAndPlanUserId(sessionId, userId).orElseThrow(
                () -> new NotFoundException("Session not found")
        );

        if (session.getStatus() != SessionStatus.PAUSED) {
            throw new NotFoundException("Session is not paused");
        }

        session.setStatus(SessionStatus.ACTIVE);
        Date now = new Date();
        Long currentPausedTime = session.getTotalPausedTime();
        Long sessionPausedTime = (now.getTime() - session.getLastPausedAt().getTime());
        session.setTotalPausedTime(currentPausedTime + sessionPausedTime);
        session.setLastPausedAt(null);
        return sessionRepository.save(session);
    }

    public T endSession(UUID sessionId, UUID userId) {
        T session = sessionRepository.findByIdAndPlanUserId(sessionId, userId).orElseThrow(
                () -> new NotFoundException("Session not found")
        );
        if (session.getStatus() == SessionStatus.COMPLETED) {
            throw new NotFoundException("Session already completed");
        }
        if (session.getStatus() == SessionStatus.PAUSED) {
            resumeSession(sessionId, userId);
        }

        Date now = new Date();
        Long sessionDuration = now.getTime() - session.getCreatedAt().getTime();
        Long sessionActiveDuration = sessionDuration - session.getTotalPausedTime();
        session.setEndAt(now);
        session.setTotalActiveTime(sessionActiveDuration);
        session.setStatus(SessionStatus.COMPLETED);
        return sessionRepository.save(session);
    }

    /**
     * This method returns the number of session ro the last N days
     *
     * @param N Number of days
     * @return List of number of sessions for each day
     */
    public List<T> getSessionsForLastNDays(int N) {
        LocalDateTime nowMinusNDays = LocalDateTime.now().minusDays(N);
        return sessionRepository.findSessionsAfterDate(nowMinusNDays);
    }

    public List<T> findByPlanUserId(UUID userId) {
        return sessionRepository.findByPlanUserId(userId);
    }
}
