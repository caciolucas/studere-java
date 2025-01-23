package com.studere.studerejava.treinere.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.studere.studerejava.framework.models.dto.request.GoalCreateOrUpdateDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class WeightGoalCreateOrUpdateDTO extends GoalCreateOrUpdateDTO {
    @NotBlank
    @JsonProperty("initial_weight")
    private Float initialWeight;
    
    @NotBlank
    @JsonProperty("target_weight")
    private Float targetWeight;
}
