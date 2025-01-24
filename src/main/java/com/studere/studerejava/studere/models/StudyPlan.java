package com.studere.studerejava.studere.models;

import com.studere.studerejava.framework.models.Plan;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("STUDERE")
public class StudyPlan extends Plan {

}
