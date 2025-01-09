package com.studere.studerejava.studere.services;

import com.studere.studerejava.framework.core.exceptions.NotFoundException;
import com.studere.studerejava.studere.models.StudereUser;
import com.studere.studerejava.studere.models.Term;
import com.studere.studerejava.studere.models.dto.CreateUpdateTermDTO;
import com.studere.studerejava.studere.repositories.StudereUserRepository;
import com.studere.studerejava.studere.repositories.TermRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("termService")
public class TermService {
    private final TermRepository termRepository;
    private final StudereUserRepository studereUserRepository;


    public TermService(TermRepository termRepository, StudereUserRepository studereUserRepository, StudereUserRepository studereUserRepository1) {
        this.termRepository = termRepository;
        this.studereUserRepository = studereUserRepository1;
    }

    // List terms for given user id
    public List<Term> listTermsByUserId(UUID userId) {
        return termRepository.findByUserId(userId);
    }

    // Create term
    public Term createTerm(CreateUpdateTermDTO createUpdateTermDTO, UUID userId) {
        StudereUser user = studereUserRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Term term = new Term();
        term.setName(createUpdateTermDTO.getName());
        term.setStartDate(createUpdateTermDTO.getStartDate());
        term.setEndDate(createUpdateTermDTO.getEndDate());
        term.setUser(user);

        return termRepository.save(term);
    }
}
