package com.studere.studerejava.studere.services;

import com.studere.studerejava.framework.core.security.JwtTokenProvider;
import com.studere.studerejava.framework.services.BaseAuthenticationService;
import com.studere.studerejava.studere.models.StudereUser;
import com.studere.studerejava.studere.repositories.StudereUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("studereAuthenticationService")
public class StudereAuthenticationService extends BaseAuthenticationService<StudereUser, StudereUserRepository> {

    @Autowired
    public StudereAuthenticationService(StudereUserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        super(userRepository, passwordEncoder, jwtTokenProvider);
    }

}