package com.studere.studerejava.treinere.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.UUID;

@Getter
public class SetCreateOrUpdateDTO {
    @NotBlank(message = "The field 'weight' is mandatory")
    private Float weight;

    @NotBlank(message = "The field 'repetitions' is mandatory")
    private Integer repetitions;

    @JsonProperty("exercise_id")
    @NotBlank(message = "The field 'exercise_id' is mandatory")
    private UUID exerciseId;
}
