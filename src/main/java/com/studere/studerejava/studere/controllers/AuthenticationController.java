package com.studere.studerejava.studere.controllers;

import com.studere.studerejava.framework.core.exceptions.BaseException;
import com.studere.studerejava.framework.core.exceptions.InvalidCredentialsException;
import com.studere.studerejava.framework.core.exceptions.NotFoundException;
import com.studere.studerejava.framework.models.User;
import com.studere.studerejava.framework.models.dto.ErrorResponseDTO;
import com.studere.studerejava.framework.models.dto.LoginRequestDTO;
import com.studere.studerejava.framework.models.dto.LoginResponseDTO;
import com.studere.studerejava.framework.models.dto.RegisterUserDTO;
import com.studere.studerejava.framework.services.BaseAuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final BaseAuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(@Qualifier("studereAuthenticationService") BaseAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterUserDTO registerUserDTO) {
        try {
            User user = authenticationService.registerUser(registerUserDTO);
            return ResponseEntity.ok(user);
        } catch (BaseException e) {
            ErrorResponseDTO errorResponse = new ErrorResponseDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (RuntimeException e) {
            ErrorResponseDTO errorResponse = new ErrorResponseDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            LoginResponseDTO loginResponse = authenticationService.login(loginRequestDTO);
            return ResponseEntity.ok(loginResponse);
        } catch (InvalidCredentialsException | NotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(e.getMessage(), HttpStatus.UNAUTHORIZED.value()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
