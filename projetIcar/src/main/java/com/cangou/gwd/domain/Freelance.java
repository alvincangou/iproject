package com.cangou.gwd.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "freelance")
public class Freelance {
    public Freelance(List<WorkingRange> workingRanges, List<Car> cars, Freelancer freelancer) {
        this.workingRanges = workingRanges;
        this.cars = cars;
        this.freelancer = freelancer;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy="freelance")
    private List<WorkingRange> workingRanges;

    @ManyToMany
    @JoinTable( name = "T_Cars_Freelances_Associations",
        joinColumns = @JoinColumn( name = "idFreelance" ),
        inverseJoinColumns = @JoinColumn( name = "idCar" ) )
    private List<Car> cars;

    @ManyToOne
    @JoinColumn(name="idFreelancer", nullable=false)
    private Freelancer freelancer;

    @OneToMany(mappedBy="freelance")
    private List<Order> orders;

    public List<WorkingRange> getWorkingRanges() {
        return workingRanges;
    }

    public void setWorkingRanges(List<WorkingRange> workingRanges) {
        this.workingRanges = workingRanges;
    }




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}
