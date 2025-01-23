package com.studere.studerejava.culinare.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.studere.studerejava.culinare.models.enums.ChefLevel;
import com.studere.studerejava.framework.models.dto.request.GoalCreateOrUpdateDTO;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class CulinaryGoalCreateOrUpdateDTO extends GoalCreateOrUpdateDTO {
    @NotNull
    @JsonProperty("chef_level")
    private ChefLevel chefLevel;

    @JsonProperty("certificate_id")
    private String certificateId;
}