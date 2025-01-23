package com.studere.studerejava.treinere.models;

import com.studere.studerejava.framework.models.Session;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("TREINERE")
public class TreinereSession extends Session {

}
