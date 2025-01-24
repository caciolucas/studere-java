package com.studere.studerejava.framework.models.dto.response;

import com.studere.studerejava.framework.models.dto.request.PlanItemDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PlanDTO<I extends PlanItemDTO> {
    private String title;
    private UUID moduleId;

    private List<I> items;
}
