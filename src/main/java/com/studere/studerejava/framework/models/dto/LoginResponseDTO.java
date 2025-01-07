package com.studere.studerejava.framework.models.dto;


import java.util.Date;
import java.util.UUID;

public class LoginResponseDTO {
    private UUID id;
    private String email;
    private String fullName;
    private Date createdAt;
    private String accessToken;
    private String tokenType;

    public LoginResponseDTO(UUID id, String email, String fullName, Date createdAt, String accessToken, String tokenType) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.createdAt = createdAt;
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }
}
