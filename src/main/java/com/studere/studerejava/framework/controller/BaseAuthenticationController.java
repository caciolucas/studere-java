package com.studere.studerejava.framework.controller;

import com.studere.studerejava.framework.core.exceptions.BaseException;
import com.studere.studerejava.framework.models.User;
import com.studere.studerejava.framework.models.dto.request.LoginRequestDTO;
import com.studere.studerejava.framework.models.dto.request.RegisterUserRequestDTO;
import com.studere.studerejava.framework.models.dto.response.ErrorResponseDTO;
import com.studere.studerejava.framework.models.dto.response.LoginResponseDTO;
import com.studere.studerejava.framework.repositories.BaseUserRepository;
import com.studere.studerejava.framework.services.BaseAuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/auth")
@RestController
public abstract class BaseAuthenticationController<T extends User, R extends BaseUserRepository<T>> {
    private final BaseAuthenticationService<T, R> authenticationService;

    public BaseAuthenticationController(BaseAuthenticationService<T, R> authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterUserRequestDTO registerUserRequestDTO) {
        try {
            User user = authenticationService.registerUser(registerUserRequestDTO);
            return ResponseEntity.ok(user);
        } catch (BaseException e) {
            throw e; // Será tratada no GlobalExceptionHandler
        } catch (RuntimeException e) {
            ErrorResponseDTO errorResponse = new ErrorResponseDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            LoginResponseDTO loginResponse = authenticationService.login(loginRequestDTO);
            return ResponseEntity.ok(loginResponse);
        } catch (BaseException e) {
            throw e; // Será tratada no GlobalExceptionHandler
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

}
