package com.studere.studerejava.framework.models;

import com.studere.studerejava.framework.models.base.BaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "modules")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Goal extends BaseModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    public Module module;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "completed_at")
    private Date completedAt;
}
