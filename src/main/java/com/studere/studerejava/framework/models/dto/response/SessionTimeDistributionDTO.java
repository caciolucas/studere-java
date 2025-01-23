package com.studere.studerejava.framework.models.dto.response;

import com.studere.studerejava.framework.models.Module;
import lombok.Setter;

@Setter
public class SessionTimeDistributionDTO {
    private Module module;

    private Long totalDuration;
}
