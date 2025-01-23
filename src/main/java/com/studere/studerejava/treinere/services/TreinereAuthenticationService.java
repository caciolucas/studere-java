package com.studere.studerejava.treinere.services;

import com.studere.studerejava.framework.core.security.JwtTokenProvider;
import com.studere.studerejava.framework.services.AuthenticationService;
import com.studere.studerejava.treinere.models.TreinereUser;
import com.studere.studerejava.treinere.repositories.TreinereUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service("treinereAuthenticationService")
public class TreinereAuthenticationService extends AuthenticationService<TreinereUser, TreinereUserRepository> {

    @Autowired
    public TreinereAuthenticationService(TreinereUserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        super(userRepository, passwordEncoder, jwtTokenProvider);
    }

    @Override
    protected TreinereUser createNewUser() {
        return new TreinereUser();
    }

}