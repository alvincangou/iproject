package com.cangou.gwd.domain;

import jdk.jfr.Enabled;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "workingRange")
public class WorkingRange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="idFreelance", nullable=false)
    private Freelance freelance;


    private Date startTime;

    private Date endTime;


    public Freelance getFreelance() {
        return freelance;
    }



    public void setFreelance(Freelance freelance) {
        this.freelance = freelance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


}
