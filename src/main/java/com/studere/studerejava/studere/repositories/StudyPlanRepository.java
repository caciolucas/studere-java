package com.studere.studerejava.studere.repositories;

import com.studere.studerejava.framework.repositories.PlanRepository;
import com.studere.studerejava.studere.models.StudyPlan;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyPlanRepository extends PlanRepository<StudyPlan> {
}
