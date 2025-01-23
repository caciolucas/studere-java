package com.studere.studerejava.culinare.models;

import com.studere.studerejava.framework.models.base.BaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "meal_ingredients")
public class MealIngredient extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @Column(name = "quantity")
    private Float quantity;

    @Column(name = "unit")
    private String unit;  // e.g. grams, ml, pieces

}