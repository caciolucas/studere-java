package com.studere.studerejava.framework.models.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.UUID;

@Getter
public class GoalCreateOrUpdateDTO {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @JsonProperty("completed_at")
    private String completedAt;

    @JsonProperty("module_id")
    private UUID moduleId;

}