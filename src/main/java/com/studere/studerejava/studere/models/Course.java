package com.studere.studerejava.studere.models;

import com.studere.studerejava.framework.models.Module;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("STUDERE")
public class Course extends Module {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "term_id", nullable = false)
    private Term term;
}
