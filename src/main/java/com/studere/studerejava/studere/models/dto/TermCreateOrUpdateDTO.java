package com.studere.studerejava.studere.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.Date;

@Getter
public class TermCreateOrUpdateDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Start Date is required")
    @JsonProperty("start_date")
    private Date startDate;

    @NotBlank(message = "End Date is required")
    @JsonProperty("end_date")
    private Date endDate;

}
