package com.studere.studerejava.studere.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public class CreateUpdateTermDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Start Date is required")
    @JsonProperty("start_date")
    private Date startDate;

    @NotBlank(message = "End Date is required")
    @JsonProperty("end_date")
    private Date endDate;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
