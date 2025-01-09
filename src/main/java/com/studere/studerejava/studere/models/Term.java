package com.studere.studerejava.studere.models;

import com.studere.studerejava.framework.models.base.BaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "terms")
public class Term extends BaseModel {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private StudereUser user;

    @OneToMany(mappedBy = "term", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Course> courses;
}
