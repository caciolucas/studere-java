package com.studere.studerejava.culinare.models;

import com.studere.studerejava.framework.models.PlanItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@DiscriminatorValue("CULINARE")
public class Meal extends PlanItem {
    @ManyToMany
    @JoinTable(
            name = "meal_ingredients",
            joinColumns = @JoinColumn(name = "meal_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients;
}
