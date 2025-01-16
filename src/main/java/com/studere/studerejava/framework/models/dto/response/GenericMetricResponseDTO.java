package com.studere.studerejava.framework.models.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class GenericMetricResponseDTO {
    private String metricName;
    private Map<String, Object> data;

    public GenericMetricResponseDTO(String metricName, Map<String, Object> data) {
        this.metricName = metricName;
        this.data = data;
    }
}
