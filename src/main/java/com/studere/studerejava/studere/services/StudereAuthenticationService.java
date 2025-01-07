package com.studere.studerejava.studere.services;

import com.studere.studerejava.framework.core.exceptions.RegisterEmailException;
import com.studere.studerejava.framework.models.dto.RegisterUserDTO;
import com.studere.studerejava.framework.services.BaseAuthenticationService;
import com.studere.studerejava.studere.models.StudereUser;
import com.studere.studerejava.studere.repositories.StudereUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("studereAuthenticationService")
public class StudereAuthenticationService implements BaseAuthenticationService {

    private final StudereUserRepository studereUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public StudereAuthenticationService(StudereUserRepository studereUserRepository, BCryptPasswordEncoder passwordEncoder) {
        this.studereUserRepository = studereUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public StudereUser registerUser(RegisterUserDTO registerUserDTO) throws RegisterEmailException {
        studereUserRepository.findByEmail(registerUserDTO.getEmail()).ifPresent(user -> {
            throw new RegisterEmailException("Este e-mail já está em uso: " + registerUserDTO.getEmail());
        });

        String hashedPassword = generateHashedPassword(registerUserDTO.getPassword());

        StudereUser newUser = new StudereUser();
        newUser.setEmail(registerUserDTO.getEmail());
        newUser.setPassword(hashedPassword);
        newUser.setFullName(registerUserDTO.getFullName());

        newUser.setTerms(new ArrayList<>());

        return studereUserRepository.save(newUser);
    }

    private String generateHashedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}