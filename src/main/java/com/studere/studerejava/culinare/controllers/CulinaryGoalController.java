package com.studere.studerejava.culinare.controllers;

import com.studere.studerejava.culinare.models.CookingGoal;
import com.studere.studerejava.culinare.models.dto.CulinaryGoalCreateOrUpdateDTO;
import com.studere.studerejava.framework.controller.GoalController;
import com.studere.studerejava.framework.core.exceptions.BaseException;
import com.studere.studerejava.framework.models.dto.response.ErrorResponseDTO;
import com.studere.studerejava.framework.services.GoalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/culinary-goals")
public class CulinaryGoalController extends GoalController<CookingGoal, CulinaryGoalCreateOrUpdateDTO> {

    private final GoalService<CookingGoal, CulinaryGoalCreateOrUpdateDTO> goalService;

    public CulinaryGoalController(GoalService<CookingGoal, CulinaryGoalCreateOrUpdateDTO> goalService) {
        super(goalService);
        this.goalService = goalService;
    }

    @Override
    @PostMapping("/")
    public ResponseEntity<?> createGoal(
            @Valid @RequestBody CulinaryGoalCreateOrUpdateDTO goalCreateOrUpdateDTO, Principal principal
    ) throws MethodArgumentNotValidException {
        try {
            CookingGoal goal = goalService.createGoal(goalCreateOrUpdateDTO, UUID.fromString(principal.getName()));
            return ResponseEntity.ok(goal);
        } catch (BaseException e) {
            throw e;
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO(e.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> updateGoal(
            @Valid @RequestBody CulinaryGoalCreateOrUpdateDTO goalCreateOrUpdateDTO,
            @PathVariable UUID id,
            Principal principal) throws MethodArgumentNotValidException {
        try {
            CookingGoal goal = goalService.updateGoal(goalCreateOrUpdateDTO, id, UUID.fromString(principal.getName()));
            return ResponseEntity.ok(goal);
        } catch (BaseException e) {
            throw e;
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO(e.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}