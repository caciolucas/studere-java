package com.studere.studerejava.framework.controller;

import com.studere.studerejava.framework.core.exceptions.BaseException;
import com.studere.studerejava.framework.models.Session;
import com.studere.studerejava.framework.models.dto.request.SessionStartDTO;
import com.studere.studerejava.framework.models.dto.response.ErrorResponseDTO;
import com.studere.studerejava.framework.services.SessionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.UUID;

public abstract class SessionController<T extends Session> {
    private final SessionService<T> sessionService;

    public SessionController(SessionService<T> sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/start")
    public ResponseEntity<?> startSession(@Valid @RequestBody SessionStartDTO sessionStartDTO, Principal principal) {
        try {
            T session = sessionService.startSession(sessionStartDTO, UUID.fromString(principal.getName()));
            return ResponseEntity.ok(session);
        } catch (BaseException e) {
            throw e;
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PostMapping("/{id}/end")
    public ResponseEntity<?> endSession(@PathVariable UUID id, Principal principal) {
        try {
            T session = sessionService.endSession(id, UUID.fromString(principal.getName()));
            return ResponseEntity.ok(session);
        } catch (BaseException e) {
            throw e;
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PostMapping("/{id}/pause")
    public ResponseEntity<?> pauseSession(@PathVariable UUID id, Principal principal) {
        try {
            T session = sessionService.pauseSession(id, UUID.fromString(principal.getName()));
            return ResponseEntity.ok(session);
        } catch (BaseException e) {
            throw e;
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PostMapping("/{id}/resume")
    public ResponseEntity<?> resumeSession(@PathVariable UUID id, Principal principal) {
        try {
            T session = sessionService.resumeSession(id, UUID.fromString(principal.getName()));
            return ResponseEntity.ok(session);
        } catch (BaseException e) {
            throw e;
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
    
}
