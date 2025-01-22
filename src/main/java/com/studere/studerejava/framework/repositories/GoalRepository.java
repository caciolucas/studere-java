package com.studere.studerejava.framework.repositories;

import com.studere.studerejava.framework.models.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface GoalRepository<T extends Goal> extends JpaRepository<T, UUID> {
    @Query("SELECT g FROM Goal g WHERE g.module.user.id = :userId AND g.id = :goalId")
    Optional<T> findByIdAndUserId(UUID goalId, UUID userId);
}
