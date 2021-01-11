package com.cangou.gwd.domain;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany
    @JoinTable( name = "T_Cars_Freelances_Associations",
        joinColumns = @JoinColumn( name = "idCar" ),
        inverseJoinColumns = @JoinColumn( name = "idFreelance" ) )
    private List<Freelance> freelances;

    private String brand;

    private String year;

    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public List<Freelance> getFreelances() {
        return freelances;
    }

    public void setFreelances(List<Freelance> freelances) {
        this.freelances = freelances;
    }


}
