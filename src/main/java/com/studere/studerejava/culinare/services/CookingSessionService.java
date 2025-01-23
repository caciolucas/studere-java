package com.studere.studerejava.culinare.services;

import com.studere.studerejava.culinare.models.CookingSession;
import com.studere.studerejava.culinare.models.Meal;
import com.studere.studerejava.culinare.models.MealPlan;
import com.studere.studerejava.framework.repositories.SessionRepository;
import com.studere.studerejava.framework.services.PlanService;
import com.studere.studerejava.framework.services.SessionService;
import org.springframework.stereotype.Service;

@Service
public class CookingSessionService extends SessionService<CookingSession> {
    public CookingSessionService(SessionRepository<CookingSession> sessionRepository, PlanService<MealPlan, Meal> planService) {
        super(sessionRepository, planService);
    }

    @Override
    protected CookingSession createNewSession() {
        return new CookingSession();
    }
}
