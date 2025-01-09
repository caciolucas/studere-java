package com.studere.studerejava.studere.controllers;

import com.studere.studerejava.framework.controller.BaseAuthenticationController;
import com.studere.studerejava.framework.services.BaseAuthenticationService;
import com.studere.studerejava.studere.models.StudereUser;
import com.studere.studerejava.studere.repositories.StudereUserRepository;
import com.studere.studerejava.studere.services.StudereAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class StudereAuthenticationController extends BaseAuthenticationController<StudereUser, StudereUserRepository> {

    @Autowired
    public StudereAuthenticationController(@Qualifier("studereAuthenticationService") BaseAuthenticationService<StudereUser, StudereUserRepository> authenticationService, StudereAuthenticationService studereAuthenticationService) {
        super(authenticationService);
    }

}
