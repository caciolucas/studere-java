package com.studere.studerejava.framework.models;

import com.studere.studerejava.framework.models.base.BaseModel;
import com.studere.studerejava.framework.models.enums.SessionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "sessions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Session extends BaseModel {
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "notes")
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SessionStatus status;

    @ManyToMany
    @JoinTable(
            name = "session_items",
            joinColumns = @JoinColumn(name = "session_id"),
            inverseJoinColumns = @JoinColumn(name = "plan_item_id")  // Changed to match the `PlanItem`'s join column
    )
    private List<PlanItem> planItems;
}
