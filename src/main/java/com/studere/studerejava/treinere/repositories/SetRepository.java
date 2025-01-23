package com.studere.studerejava.treinere.repositories;

import com.studere.studerejava.treinere.models.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface SetRepository extends JpaRepository<Set, UUID> {
}
