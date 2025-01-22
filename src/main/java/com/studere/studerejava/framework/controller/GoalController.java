package com.studere.studerejava.framework.controller;

import com.studere.studerejava.framework.core.exceptions.BaseException;
import com.studere.studerejava.framework.models.Goal;
import com.studere.studerejava.framework.models.dto.request.GoalCreateOrUpdateDTO;
import com.studere.studerejava.framework.models.dto.response.ErrorResponseDTO;
import com.studere.studerejava.framework.services.GoalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.UUID;

public abstract class GoalController<T extends Goal> {
    private final GoalService<T> goalService;

    public GoalController(GoalService<T> goalService) {
        this.goalService = goalService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createGoal(@Valid @RequestBody GoalCreateOrUpdateDTO goalCreateOrUpdateDTO, Principal principal) throws MethodArgumentNotValidException {
        try {
            T goal = goalService.createGoal(goalCreateOrUpdateDTO, UUID.fromString(principal.getName()));
            return ResponseEntity.ok(goal);
        } catch (BaseException e) {
            throw e;
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGoal(@Valid @RequestBody GoalCreateOrUpdateDTO goalCreateOrUpdateDTO, @PathVariable UUID id, Principal principal) throws MethodArgumentNotValidException {
        try {
            T goal = goalService.updateGoal(goalCreateOrUpdateDTO, id, UUID.fromString(principal.getName()));
            return ResponseEntity.ok(goal);
        } catch (BaseException e) {
            throw e;
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO(e.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value())
                    );
        }
    }
}
