package com.studere.studerejava.studere.services;

import com.studere.studerejava.framework.repositories.SessionRepository;
import com.studere.studerejava.framework.services.PlanService;
import com.studere.studerejava.framework.services.SessionService;
import com.studere.studerejava.studere.models.StudyPlan;
import com.studere.studerejava.studere.models.StudyPlanTopic;
import com.studere.studerejava.studere.models.StudySession;
import org.springframework.stereotype.Service;

@Service
public class StudySessionService extends SessionService<StudySession> {

    public StudySessionService(
            SessionRepository<StudySession> sessionRepository,
            PlanService<StudyPlan, StudyPlanTopic> planService
    ) {
        super(sessionRepository, planService);
    }

    @Override
    protected StudySession createNewSession() {
        return new StudySession();
    }
}
