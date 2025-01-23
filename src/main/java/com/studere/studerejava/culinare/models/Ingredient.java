package com.studere.studerejava.culinare.models;

import com.studere.studerejava.framework.models.base.BaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@DiscriminatorValue("CULINARE")
public class Ingredient extends BaseModel {
    @Column(name = "name")
    private String name;

    @Column(name = "proteins_per_unit")
    private Integer proteinsPerUnit;

    @Column(name = "carbs_per_unit")
    private Integer carbsPerUnit;

    @Column(name = "fats_per_unit")
    private Integer fatsPerUnit;

    @Column(name = "calories_per_unit")
    private Integer caloriesPerUnit;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL)
    private List<MealIngredient> mealIngredients;
}
