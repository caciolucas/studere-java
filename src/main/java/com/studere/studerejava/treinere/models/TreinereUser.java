package com.studere.studerejava.treinere.models;

import com.studere.studerejava.framework.models.User;
import com.studere.studerejava.treinere.models.enums.ExperienceEnum;
import com.studere.studerejava.treinere.models.enums.ObjectiveEnum;
import com.studere.studerejava.treinere.models.enums.SexEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("TREINERE")
public class TreinereUser extends User {


    @Column(name = "age")
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private SexEnum sex;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "height")
    private Float height;

    @Enumerated(EnumType.STRING)
    @Column(name = "objective")
    private ObjectiveEnum objective;

    @Enumerated(EnumType.STRING)
    @Column(name = "experience")
    private ExperienceEnum experience;
}