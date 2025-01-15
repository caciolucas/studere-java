package com.studere.studerejava.framework.models.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class PlanCreateOrUpdateDTO {
    @NotBlank(message = "'title' is required")
    private String title;

    @JsonProperty("module_id")
    @NotBlank(message = "'module_id' is required")
    private UUID moduleId;

    private List<PlanItemDTO> items;
}
