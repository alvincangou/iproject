package com.cangou.gwd.domain;

import javax.persistence.*;

@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="idFreelance", nullable=false)
    private Freelance freelance;

    @ManyToOne
    @JoinColumn(name="idUser", nullable=false)
    private User user;

    private String status;

    @OneToOne  @JoinColumn( name="idWorkingRange" )
    private WorkingRange workingRange;

    private String request;

    public WorkingRange getWorkingRange() {
        return workingRange;
    }

    public void setWorkingRange(WorkingRange workingRange) {
        this.workingRange = workingRange;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }



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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
