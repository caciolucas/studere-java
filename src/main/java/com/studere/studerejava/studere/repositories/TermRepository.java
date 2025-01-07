package com.studere.studerejava.studere.repositories;

import com.studere.studerejava.studere.models.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TermRepository extends JpaRepository<Term, UUID> {
    List<Term> findByUserId(UUID userId);
}
