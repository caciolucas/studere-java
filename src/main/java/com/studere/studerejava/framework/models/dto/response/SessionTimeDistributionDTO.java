package com.studere.studerejava.framework.models.dto.response;

import com.studere.studerejava.studere.models.Course;
import lombok.Setter;

@Setter
public class SessionTimeDistributionDTO {
    private Course course;

    private Long totalDuration;
}
