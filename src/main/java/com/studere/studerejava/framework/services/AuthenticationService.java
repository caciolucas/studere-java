package com.studere.studerejava.framework.services;

import com.studere.studerejava.framework.core.security.JwtTokenProvider;
import com.studere.studerejava.framework.models.User;
import com.studere.studerejava.framework.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("authenticationService")
public class AuthenticationService extends BaseAuthenticationService<User, UserRepository> {

    @Autowired
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        super(userRepository, passwordEncoder, jwtTokenProvider);
    }

}