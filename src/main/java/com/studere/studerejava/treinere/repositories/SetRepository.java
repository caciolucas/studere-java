package com.studere.studerejava.treinere.repositories;

import com.studere.studerejava.treinere.models.Set;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SetRepository extends JpaRepository<Set, UUID> {
    List<Set> findByUserId(UUID userId);

    Optional<Set> findByIdAndUserId(UUID id, UUID userId);

    void deleteByIdAndUserId(UUID id, UUID userId);
}
