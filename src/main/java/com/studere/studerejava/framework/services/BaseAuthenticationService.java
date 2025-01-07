package com.studere.studerejava.framework.services;

import com.studere.studerejava.framework.core.exceptions.RegisterEmailException;
import com.studere.studerejava.framework.models.User;
import com.studere.studerejava.framework.models.dto.RegisterUserDTO;

public interface BaseAuthenticationService {
    User registerUser(RegisterUserDTO registerUserDTO) throws RegisterEmailException;
}