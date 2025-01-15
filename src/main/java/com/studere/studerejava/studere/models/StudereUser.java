package com.studere.studerejava.studere.models;

import com.studere.studerejava.framework.models.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
@DiscriminatorValue("STUDERE")
public class StudereUser extends User {

    // Getters and Setters
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Term> terms;

}
