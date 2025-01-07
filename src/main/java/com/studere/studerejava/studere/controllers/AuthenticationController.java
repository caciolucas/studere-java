package com.studere.studerejava.studere.controllers;

import com.studere.studerejava.framework.core.exceptions.BaseException;
import com.studere.studerejava.framework.models.User;
import com.studere.studerejava.framework.models.dto.ErrorResponseDTO;
import com.studere.studerejava.framework.models.dto.RegisterUserDTO;
import com.studere.studerejava.framework.services.BaseAuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        }
    }
}
