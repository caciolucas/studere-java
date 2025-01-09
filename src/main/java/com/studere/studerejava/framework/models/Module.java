package com.studere.studerejava.framework.models;

import com.studere.studerejava.framework.models.base.BaseModel;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Module extends BaseModel {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
