package com.studere.studerejava.framework.models.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class SessionStreakDTO {
    private Date referenceDate;
    
    private boolean completed;
}
