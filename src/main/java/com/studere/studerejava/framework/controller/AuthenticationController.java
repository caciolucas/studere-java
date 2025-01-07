package com.studere.studerejava.framework.controller;

import com.studere.studerejava.framework.core.exceptions.BaseException;
import com.studere.studerejava.framework.models.User;
import com.studere.studerejava.framework.models.dto.RegisterUserDTO;
import com.studere.studerejava.framework.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/auth")
@RestController
public abstract class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(@Qualifier("authenticationService") AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterUserDTO registerUserDTO) {
        try {
            User user = authenticationService.registerUser(registerUserDTO);
            return ResponseEntity.ok().body("Usuário registrado com sucesso: " + user.getEmail());
        } catch (BaseException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
