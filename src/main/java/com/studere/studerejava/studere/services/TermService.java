package com.studere.studerejava.studere.services;

import com.studere.studerejava.framework.core.exceptions.NotFoundException;
import com.studere.studerejava.studere.models.StudereUser;
import com.studere.studerejava.studere.models.Term;
import com.studere.studerejava.studere.models.dto.TermCreateOrUpdateDTO;
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
    public Term createTerm(TermCreateOrUpdateDTO termCreateOrUpdateDTO, UUID userId) {
        StudereUser user = studereUserRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Term term = new Term();
        term.setName(termCreateOrUpdateDTO.getName());
        term.setStartDate(termCreateOrUpdateDTO.getStartDate());
        term.setEndDate(termCreateOrUpdateDTO.getEndDate());
        term.setUser(user);

        return termRepository.save(term);
    }

    // Get term by id
    public Term getTermById(UUID id, UUID userId) {
        return termRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new NotFoundException("Term not found"));
    }

    // Delete term by id
    public void deleteTermById(UUID id, UUID userId) {
        termRepository.deleteByIdAndUserId(id, userId);
    }

    // Update term by id
    public Term updateTermById(UUID id, TermCreateOrUpdateDTO termCreateOrUpdateDTO, UUID userId) {
        Term term = termRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new NotFoundException("Term not found"));

        term.setName(termCreateOrUpdateDTO.getName());
        term.setStartDate(termCreateOrUpdateDTO.getStartDate());
        term.setEndDate(termCreateOrUpdateDTO.getEndDate());

        return termRepository.save(term);
    }
}
