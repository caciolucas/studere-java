package com.studere.studerejava.culinare.models;

import com.studere.studerejava.framework.models.Module;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CULINARE")
public class CulinaryModule extends Module {
}
