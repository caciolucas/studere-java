package com.studere.studerejava.treinere.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.studere.studerejava.framework.models.dto.request.PlanItemDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ExerciseDTO extends PlanItemDTO {
    @JsonProperty("weight_suggestion")
    @NotBlank(message = "Weight suggestion is required")
    private Float weightSuggestion;

    @JsonProperty("sets_suggestion")
    @NotBlank(message = "Sets suggestion is required")
    private Integer setsSuggestion;

    @JsonProperty("reps_suggestion")
    @NotBlank(message = "Reps suggestion is required")
    private Integer repsSuggestion;
}
