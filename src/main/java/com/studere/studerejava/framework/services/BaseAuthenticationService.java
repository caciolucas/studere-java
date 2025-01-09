package com.studere.studerejava.framework.services;

import com.studere.studerejava.framework.core.exceptions.InvalidCredentialsException;
import com.studere.studerejava.framework.core.exceptions.RegisterEmailException;
import com.studere.studerejava.framework.core.security.JwtTokenProvider;
import com.studere.studerejava.framework.models.User;
import com.studere.studerejava.framework.models.dto.request.LoginRequestDTO;
import com.studere.studerejava.framework.models.dto.request.RegisterUserRequestDTO;
import com.studere.studerejava.framework.models.dto.response.LoginResponseDTO;
import com.studere.studerejava.framework.repositories.BaseUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class BaseAuthenticationService<T extends User, R extends BaseUserRepository<T>> {
    private final R userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public BaseAuthenticationService(R userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    protected abstract T createNewUser();

    public T registerUser(RegisterUserRequestDTO registerUserRequestDTO) throws RegisterEmailException {
        userRepository.findByEmail(registerUserRequestDTO.getEmail()).ifPresent(user -> {
            throw new RegisterEmailException("Este e-mail já está em uso: " + registerUserRequestDTO.getEmail());
        });

        String hashedPassword = passwordEncoder.encode(registerUserRequestDTO.getPassword());

        T newUser = createNewUser();
        newUser.setEmail(registerUserRequestDTO.getEmail());
        newUser.setPassword(hashedPassword);
        newUser.setFullName(registerUserRequestDTO.getFullName());

        return userRepository.save(newUser);
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Optional<T> maybeUser = userRepository.findByEmail(loginRequestDTO.getEmail());

        T user = userRepository.findByEmail(loginRequestDTO.getEmail()).orElseThrow(() -> new InvalidCredentialsException("Credenciais inválidas"));

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Credenciais inválidas");
        }

        // Create access token
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", user.getId());
        claims.put("iat", Instant.now().getEpochSecond()); // Issued at
        claims.put("roles", List.of("ROLE_USER"));

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
}