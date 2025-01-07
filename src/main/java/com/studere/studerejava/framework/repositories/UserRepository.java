package com.studere.studerejava.framework.repositories;

import com.studere.studerejava.framework.models.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseUserRepository<User> {
    // Métodos específicos para User, se necessário
}