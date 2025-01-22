package com.studere.studerejava.framework.repositories;

import com.studere.studerejava.framework.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface SessionRepository<T extends Session> extends JpaRepository<T, UUID> {
    List<T> findByPlanId(UUID planId);


    @Query("SELECT s FROM Session s WHERE s.plan.module.user.id = :userId AND s.id = :sessionId")
    Optional<T> findByIdAndPlanUserId(UUID sessionId, UUID userId);

    @Query("SELECT s FROM Session s WHERE s.plan.id = :planId AND s.status = 'ACTIVE'")
    Optional<T> findActiveSessionByPlanId(UUID planId);

    @Query("SELECT s FROM Session s WHERE s.createdAt >= :start")
    List<T> findSessionsAfterDate(@Param("start") LocalDateTime start);

}
