package com.studere.studerejava.studere.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.studere.studerejava.framework.models.dto.request.GoalCreateOrUpdateDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.Date;

@Getter
public class ActivityCreateOrUpdateDTO extends GoalCreateOrUpdateDTO {
    private Double grade;

    @JsonProperty("due_at")
    @NotNull(message = "'due_at' is required")
    private Date dueAt;
}
