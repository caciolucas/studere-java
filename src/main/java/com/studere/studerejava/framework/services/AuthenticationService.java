package com.studere.studerejava.framework.services;

import com.studere.studerejava.framework.core.exceptions.InvalidCredentialsException;
import com.studere.studerejava.framework.core.exceptions.RegisterEmailException;
import com.studere.studerejava.framework.core.security.JwtTokenProvider;
import com.studere.studerejava.framework.models.User;
import com.studere.studerejava.framework.models.dto.LoginRequestDTO;
import com.studere.studerejava.framework.models.dto.LoginResponseDTO;
import com.studere.studerejava.framework.models.dto.RegisterUserDTO;
import com.studere.studerejava.framework.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service("authenticationService")
public class AuthenticationService extends BaseAuthenticationService<User, UserRepository> {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthenticationService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
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

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) throws InvalidCredentialsException {
        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Credenciais inválidas"));

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Credenciais inválidas");
        }

        // Create access token
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", user.getId());
        claims.put("iat", Instant.now().getEpochSecond()); // Issued at

        String token = jwtTokenProvider.createToken(claims);

        return new LoginResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getCreatedAt(),
                token,
                "bearer"
        );

    }

    private String generateHashedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}