package com.studere.studerejava.culinare.models.dto;

import com.studere.studerejava.framework.models.dto.request.PlanItemDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MealDTO extends PlanItemDTO {

    private List<IngredientDTO> ingredients;
    
}