package com.studere.studerejava.studere.repositories;

import com.studere.studerejava.framework.repositories.GoalRepository;
import com.studere.studerejava.studere.models.Activity;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends GoalRepository<Activity> {
}
