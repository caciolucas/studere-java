package com.studere.studerejava.treinere.models;

import com.studere.studerejava.framework.models.base.BaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "set")
public class Set extends BaseModel {
    @Column(name = "weight")
    private Float weight;

    @Column(name = "repetitions")
    private Integer repetitions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;
}
