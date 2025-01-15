package com.studere.studerejava.studere.models.dto;

import com.studere.studerejava.framework.models.dto.request.ModuleCreateOrUpdateDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CourseCreateOrUpdateDTO extends ModuleCreateOrUpdateDTO {
    @NotBlank(message = "Term ID is required")
    private UUID termId;
}
