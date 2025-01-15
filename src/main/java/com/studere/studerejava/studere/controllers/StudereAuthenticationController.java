package com.studere.studerejava.studere.controllers;

import com.studere.studerejava.framework.controller.AuthenticationController;
import com.studere.studerejava.framework.services.AuthenticationService;
import com.studere.studerejava.studere.models.StudereUser;
import com.studere.studerejava.studere.repositories.StudereUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class StudereAuthenticationController extends AuthenticationController<StudereUser, StudereUserRepository> {

    @Autowired
    public StudereAuthenticationController(@Qualifier("studereAuthenticationService") AuthenticationService<StudereUser, StudereUserRepository> authenticationService) {
        super(authenticationService);
    }

}
