package com.studere.studerejava.culinare.models;

import com.studere.studerejava.culinare.models.enums.ChefLevel;
import com.studere.studerejava.framework.models.Goal;
import jakarta.persistence.*;


@Entity
@DiscriminatorValue("CULINARE")
public class CookingGoal extends Goal {
    @Enumerated(EnumType.STRING)
    @Column(name = "chef_level", nullable = false)
    private ChefLevel chefLevel;

    @Column(name = "certificate_id")
    private String certificateId;
}

