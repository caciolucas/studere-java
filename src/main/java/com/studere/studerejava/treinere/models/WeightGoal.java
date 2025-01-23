package com.studere.studerejava.treinere.models;

import com.studere.studerejava.framework.models.Goal;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("TREINERE")
public class WeightGoal extends Goal {
    @Column(name = "initial_weight")
    private Float initialWeight;

    @Column(name = "target_weight")
    private Float targetWeight;
}
