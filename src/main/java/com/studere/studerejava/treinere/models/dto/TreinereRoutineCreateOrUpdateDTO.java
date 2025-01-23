package com.studere.studerejava.treinere.models.dto;

import com.studere.studerejava.framework.models.dto.request.PlanCreateOrUpdateDTO;
import com.studere.studerejava.treinere.models.enums.TrainTypeEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.List;

@Getter
public class TreinereRoutineCreateOrUpdateDTO extends PlanCreateOrUpdateDTO {
    @NotBlank(message = "Type is required")
    private TrainTypeEnum type;

    private List<ExerciseDTO> items;
}
