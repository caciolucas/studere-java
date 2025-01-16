package com.studere.studerejava.framework.repositories;

import com.studere.studerejava.framework.models.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlanRepository<T extends Plan> extends JpaRepository<T, UUID> {
    @Query("SELECT p FROM Plan p WHERE p.id = :planId AND p.module.user.id = :userId")
    Optional<T> findByIdAndModuleUserId(@Param("planId") UUID planId, @Param("userId") UUID userId);

    @Modifying
    @Query("DELETE FROM Plan p WHERE p.id = :planId AND p.module.user.id = :userId")
    void deleteByIdAndModuleUserId(@Param("planId") UUID planId, @Param("userId") UUID userId);

    @Query("SELECT p FROM Plan p WHERE p.module.user.id = :userId")
    List<T> findByModuleUserId(@Param("userId") UUID userId);
}
