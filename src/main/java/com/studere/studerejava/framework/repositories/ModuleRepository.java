package com.studere.studerejava.framework.repositories;

import com.studere.studerejava.framework.models.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface ModuleRepository<T extends Module> extends JpaRepository<T, UUID> {
    Optional<T> findByIdAndUserId(UUID moduleId, UUID userId);

    List<T> findByUserId(UUID userId);

    void deleteByIdAndUserId(UUID moduleId, UUID userId);
}
