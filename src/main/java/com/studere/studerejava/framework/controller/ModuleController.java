package com.studere.studerejava.framework.controller;

import com.studere.studerejava.framework.core.exceptions.BaseException;
import com.studere.studerejava.framework.models.Module;
import com.studere.studerejava.framework.models.dto.request.ModuleCreateOrUpdateDTO;
import com.studere.studerejava.framework.models.dto.response.ErrorResponseDTO;
import com.studere.studerejava.framework.services.ModuleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

public abstract class ModuleController<T extends Module> {
    protected final ModuleService<T> moduleService;

    public ModuleController(ModuleService<T> moduleService) {
        this.moduleService = moduleService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createModule(@Valid @RequestBody ModuleCreateOrUpdateDTO moduleCreateOrUpdateDTO, Principal principal) {
        try {
            return ResponseEntity.ok(moduleService.createModule(moduleCreateOrUpdateDTO));
        } catch (BaseException e) {
            throw e;
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getModules(Principal principal) {
        try {
            return ResponseEntity.ok(moduleService.listModulesByUserId(UUID.fromString(principal.getName())));
        } catch (BaseException e) {
            throw e;
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getModule(@PathVariable UUID id, Principal principal) {
        try {
            return ResponseEntity.ok(moduleService.findModuleById(id, UUID.fromString(principal.getName())));
        } catch (BaseException e) {
            throw e;
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateModule(@PathVariable UUID id, @Valid @RequestBody ModuleCreateOrUpdateDTO moduleCreateOrUpdateDTO, Principal principal) {
        try {
            return ResponseEntity.ok(moduleService.updateModule(moduleCreateOrUpdateDTO, id, UUID.fromString(principal.getName())));
        } catch (BaseException e) {
            throw e;
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteModule(@PathVariable UUID id, Principal principal) {
        try {
            moduleService.deleteModule(id, UUID.fromString(principal.getName()));
            return ResponseEntity.ok().build();
        } catch (BaseException e) {
            throw e;
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
