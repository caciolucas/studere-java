package com.studere.studerejava.studere.repositories;

import com.studere.studerejava.framework.repositories.BaseUserRepository;
import com.studere.studerejava.studere.models.StudereUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudereUserRepository extends BaseUserRepository {

    @EntityGraph(attributePaths = {"terms"})
    Optional<StudereUser> findById(UUID id);

    @Override
    @EntityGraph(attributePaths = {"terms"})
    Optional<StudereUser> findByEmail(String email);

}
