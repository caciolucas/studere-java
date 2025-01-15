package com.studere.studerejava.framework.models;

import com.studere.studerejava.framework.models.base.BaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "modules")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Module extends BaseModel {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
