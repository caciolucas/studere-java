package com.studere.studerejava.framework.controller;

import com.studere.studerejava.framework.core.exceptions.BaseException;
import com.studere.studerejava.framework.models.Plan;
import com.studere.studerejava.framework.models.PlanItem;
import com.studere.studerejava.framework.models.dto.request.PlanAiGenerateDTO;
import com.studere.studerejava.framework.models.dto.request.PlanCreateOrUpdateDTO;
import com.studere.studerejava.framework.models.dto.response.ErrorResponseDTO;
import com.studere.studerejava.framework.models.dto.response.PlanDTO;
import com.studere.studerejava.framework.services.PlanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

public abstract class PlanController<T extends Plan, U extends PlanItem> {
    protected final PlanService<T, U> planService;

    public PlanController(PlanService<T, U> planService) {
        this.planService = planService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createPlan(@Valid @RequestBody PlanCreateOrUpdateDTO planCreateOrUpdateDTO, Principal principal) {
        try {
            T module = planService.createPlan(planCreateOrUpdateDTO, UUID.fromString(principal.getName()));
            return ResponseEntity.ok(module);
        } catch (BaseException e) {
            throw e;
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getPlans(Principal principal) {
        try {
            return ResponseEntity.ok(planService.listPlansByUserId(UUID.fromString(principal.getName())));
        } catch (BaseException e) {
            throw e;
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPlan(@PathVariable UUID id, Principal principal) {
        try {
            return ResponseEntity.ok(planService.findPlanById(id, UUID.fromString(principal.getName())));
        } catch (BaseException e) {
            throw e;
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlan(@PathVariable UUID id, @Valid @RequestBody PlanCreateOrUpdateDTO planCreateOrUpdateDTO, Principal principal) {
        try {
            return ResponseEntity.ok(planService.updatePlan(planCreateOrUpdateDTO, id, UUID.fromString(principal.getName())));
        } catch (BaseException e) {
            throw e;
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlan(@PathVariable UUID id, Principal principal) {
        try {
            planService.deletePlan(id, UUID.fromString(principal.getName()));
            return ResponseEntity.ok().build();
        } catch (BaseException e) {
            throw e;
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PostMapping("/ai-generate")
    public ResponseEntity<?> aiGenerate(@Valid @RequestBody PlanAiGenerateDTO planAiGenerateDTO, Principal principal) {
        try {
            PlanDTO plan = planService.aiGeneratePlan(planAiGenerateDTO, UUID.fromString(principal.getName()));
            return ResponseEntity.ok(plan);
        } catch (BaseException e) {
            throw e;
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

}
