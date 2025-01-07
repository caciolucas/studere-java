package com.studere.studerejava.framework.services;

import com.studere.studerejava.framework.core.exceptions.RegisterEmailException;
import com.studere.studerejava.framework.models.User;
import com.studere.studerejava.framework.models.dto.RegisterUserDTO;
import com.studere.studerejava.framework.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("authenticationService")
public class AuthenticationService implements BaseAuthenticationService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(RegisterUserDTO registerUserDTO) throws RegisterEmailException {
        userRepository.findByEmail(registerUserDTO.getEmail()).ifPresent(user -> {
            throw new RegisterEmailException("Este e-mail já está em uso: " + registerUserDTO.getEmail());
        });

        String hashedPassword = generateHashedPassword(registerUserDTO.getPassword());

        User newUser = new User();
        newUser.setEmail(registerUserDTO.getEmail());
        newUser.setPassword(hashedPassword);
        newUser.setFullName(registerUserDTO.getFullName());

        return userRepository.save(newUser);
    }

    private String generateHashedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}