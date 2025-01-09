package com.studere.studerejava.studere.models;

import com.studere.studerejava.framework.models.base.BaseModel;
import jakarta.persistence.*;

import java.util.Date;

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

    // @OneToMany(mappedBy = "term", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<Course> courses;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public StudereUser getUser() {
        return user;
    }

    public void setUser(StudereUser user) {
        this.user = user;
    }

}
