package com.studere.studerejava.framework.models.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SessionTimeDistributionDTO {
    private UUID moduleId;
    private String moduleName;
    private Long totalDuration;
}
