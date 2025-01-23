package com.studere.studerejava.treinere.models;

import com.studere.studerejava.framework.models.Plan;
import com.studere.studerejava.treinere.models.enums.TrainTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("TREINERE")
public class TreinereRoutine extends Plan {

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TrainTypeEnum type;

}
