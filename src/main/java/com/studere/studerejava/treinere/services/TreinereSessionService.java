package com.studere.studerejava.treinere.services;

import com.studere.studerejava.framework.repositories.ModuleRepository;
import com.studere.studerejava.framework.repositories.SessionRepository;
import com.studere.studerejava.framework.services.PlanService;
import com.studere.studerejava.framework.services.SessionService;
import com.studere.studerejava.treinere.models.Exercise;
import com.studere.studerejava.treinere.models.TreinereRoutine;
import com.studere.studerejava.treinere.models.TreinereSession;

public class TreinereSessionService extends SessionService<TreinereSession> {
  private final SetService setService;

  public TreinereSessionService(
    SessionRepository<TreinereSession> sessionRepository,
    PlanService<TreinereRoutine, Exercise> planService,
    SetService setService
  ) {
    super(sessionRepository, planService);
    this.setService = setService;
  }

  @Override
  protected TreinereSession createNewSession() {
    return new TreinereSession();
  }
}
