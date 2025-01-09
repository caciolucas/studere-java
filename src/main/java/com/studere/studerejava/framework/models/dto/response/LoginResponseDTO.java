package com.studere.studerejava.framework.models.dto.response;

import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
public class LoginResponseDTO {
    private final UUID id;
    private final String email;
    private final String fullName;
    private final Date createdAt;
    private final String accessToken;
    private final String tokenType;

    public LoginResponseDTO(UUID id, String email, String fullName, Date createdAt, String accessToken, String tokenType) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.createdAt = createdAt;
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }

}
