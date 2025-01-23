package com.studere.studerejava.treinere.models;

import com.studere.studerejava.framework.models.Module;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("TREINERE")
public class TreinereProgram extends Module {

}
