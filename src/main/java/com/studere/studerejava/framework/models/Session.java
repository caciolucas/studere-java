package com.studere.studerejava.framework.models;

import com.studere.studerejava.framework.models.base.BaseModel;
import com.studere.studerejava.framework.models.enums.SessionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class Session extends BaseModel {
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "notes")
    private String notes;

    @Enumerated
    @Column(name = "status", nullable = false)
    private SessionStatus status;

    @ManyToMany(mappedBy = "sessions")
    private List<PlanItem> planItems;
}
