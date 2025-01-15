package com.studere.studerejava.studere.models;

import com.studere.studerejava.framework.models.Session;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("STUDERE")
public class StudySession extends Session {
}
