package com.cangou.gwd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

/**
 * A Work.
 */
@Entity
@Table(name = "work")
public class Work implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "necessary_time")
    private Duration necessaryTime;

    @Column(name = "price")
    private Double price;

    @ManyToOne
    @JsonIgnoreProperties(value = "works", allowSetters = true)
    private Freelancer doneBy;

    @ManyToMany
    @JoinTable(name = "work_executed_on",
               joinColumns = @JoinColumn(name = "work_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "executed_on_id", referencedColumnName = "id"))
    private Set<Freelance> executedOns = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Work name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Duration getNecessaryTime() {
        return necessaryTime;
    }

    public Work necessaryTime(Duration necessaryTime) {
        this.necessaryTime = necessaryTime;
        return this;
    }

    public void setNecessaryTime(Duration necessaryTime) {
        this.necessaryTime = necessaryTime;
    }

    public Double getPrice() {
        return price;
    }

    public Work price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Freelancer getDoneBy() {
        return doneBy;
    }

    public Work doneBy(Freelancer freelancer) {
        this.doneBy = freelancer;
        return this;
    }

    public void setDoneBy(Freelancer freelancer) {
        this.doneBy = freelancer;
    }

    public Set<Freelance> getExecutedOns() {
        return executedOns;
    }

    public Work executedOns(Set<Freelance> freelances) {
        this.executedOns = freelances;
        return this;
    }

    public Work addExecutedOn(Freelance freelance) {
        this.executedOns.add(freelance);
        freelance.getExecutes().add(this);
        return this;
    }

    public Work removeExecutedOn(Freelance freelance) {
        this.executedOns.remove(freelance);
        freelance.getExecutes().remove(this);
        return this;
    }

    public void setExecutedOns(Set<Freelance> freelances) {
        this.executedOns = freelances;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Work)) {
            return false;
        }
        return id != null && id.equals(((Work) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Work{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", necessaryTime='" + getNecessaryTime() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
