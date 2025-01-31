package com.studere.studerejava.studere.controllers;

import com.studere.studerejava.framework.core.exceptions.BaseException;
import com.studere.studerejava.framework.models.dto.response.ErrorResponseDTO;
import com.studere.studerejava.studere.models.Term;
import com.studere.studerejava.studere.models.dto.TermCreateOrUpdateDTO;
import com.studere.studerejava.studere.services.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/terms")
public class TermController {

    private final TermService termService;

    @Autowired
    public TermController(TermService termService) {
        this.termService = termService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createTerm(@RequestBody TermCreateOrUpdateDTO termCreateOrUpdateDTO, Principal principal) {
        try {
            Term term = termService.createTerm(termCreateOrUpdateDTO, UUID.fromString(principal.getName()));
            return ResponseEntity.ok(term);
        } catch (BaseException e) {
            throw e;
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getTerms(Principal principal) {
        try {
            return ResponseEntity.ok(termService.listTermsByUserId(UUID.fromString(principal.getName())));
        } catch (BaseException e) {
            throw e;
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTerm(@PathVariable UUID id, Principal principal) {
        try {
            return ResponseEntity.ok(termService.getTermById(id, UUID.fromString(principal.getName())));
        } catch (BaseException e) {
            throw e;
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTerm(@PathVariable UUID id, Principal principal) {
        try {
            termService.deleteTermById(id, UUID.fromString(principal.getName()));
            return ResponseEntity.ok().build();
        } catch (BaseException e) {
            throw e;
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTerm(@PathVariable UUID id, @RequestBody TermCreateOrUpdateDTO termCreateOrUpdateDTO, Principal principal) {
        try {
            Term term = termService.updateTermById(id, termCreateOrUpdateDTO, UUID.fromString(principal.getName()));
            return ResponseEntity.ok(term);
        } catch (BaseException e) {
            throw e;
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
