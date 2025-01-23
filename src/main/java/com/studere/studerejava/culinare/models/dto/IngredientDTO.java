package com.studere.studerejava.culinare.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Proteins per unit is required")
    @JsonProperty("proteins_per_unit")
    private Integer proteinsPerUnit;

    @NotBlank(message = "Carbs per unit is required")
    @JsonProperty("carbs_per_unit")
    private Integer carbsPerUnit;

    @NotBlank(message = "Fats per unit is required")
    @JsonProperty("fats_per_unit")
    private Integer fatsPerUnit;

    @NotBlank(message = "Calories per unit is required")
    @JsonProperty("calories_per_unit")
    private Integer caloriesPerUnit;

}