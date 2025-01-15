package com.studere.studerejava.studere.models;

import com.studere.studerejava.framework.models.PlanItem;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("STUDERE")
public class StudyPlanTopic extends PlanItem {
}
