package com.studere.studerejava.framework.models.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.UUID;

@Getter
public class PlanAiGenerateDTO {
    @JsonProperty("module_id")
    @NotBlank(message = "O campo 'module_id' é obrigatório")
    public UUID moduleId;

    public String prompt;
}
