package com.studere.studerejava.framework.repositories;

import com.studere.studerejava.framework.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface BaseUserRepository<T extends User> extends JpaRepository<T, UUID> {
    Optional<T> findByEmail(String email);
}