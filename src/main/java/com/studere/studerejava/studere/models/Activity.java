package com.studere.studerejava.studere.models;

import com.studere.studerejava.framework.models.Goal;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@DiscriminatorValue("STUDERE")
public class Activity extends Goal {
    @Column(name = "grade")
    private Double grade;

    @Column(name = "due_at", nullable = false)
    private Date dueAt;
}
