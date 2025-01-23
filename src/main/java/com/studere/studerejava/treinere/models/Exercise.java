package com.studere.studerejava.treinere.models;

import com.studere.studerejava.framework.models.PlanItem;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("TREINERE")
public class Exercise extends PlanItem {
    @Column(name = "weight_suggestion")
    private Float weightSuggestion;

    @Column(name = "sets_suggestion")
    private Integer setsSuggestion;

    @Column(name = "reps_suggestion")
    private Integer repsSuggestion;
}
