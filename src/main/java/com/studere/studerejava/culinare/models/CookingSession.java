package com.studere.studerejava.culinare.models;

import com.studere.studerejava.framework.models.Session;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CULINARE")
public class CookingSession extends Session {
}
