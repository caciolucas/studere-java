package com.studere.studerejava.framework.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterUserDTO {
    private String email;
    private String password;
    @JsonProperty("full_name")
    private String fullName;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }
}
