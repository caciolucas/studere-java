package com.studere.studerejava.studere.models;

import com.studere.studerejava.framework.models.Session;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "study_sessions")
public class StudySession extends Session {
}
