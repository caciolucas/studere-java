package com.studere.studerejava.framework.repositories;

import com.studere.studerejava.framework.models.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlanRepository<T extends Plan> extends JpaRepository<T, UUID> {
    Optional<T> findByIdAndUserId(UUID planId, UUID userId);

    void deleteByIdAndUserId(UUID planId, UUID userId);

    List<T> findByUserId(UUID userId);
}
