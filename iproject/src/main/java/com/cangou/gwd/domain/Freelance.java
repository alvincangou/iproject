package com.cangou.gwd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Set;

/**
 * A Freelance.
 */
@Entity
@Table(name = "freelance")
public class Freelance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = "freelances", allowSetters = true)
    private Freelancer belong;

    @ManyToMany
    @JoinTable(name = "work_executed_on",
        joinColumns=@JoinColumn(name = "executed_on_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "work_id", referencedColumnName = "id"))
    private Set<Work> executes;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Freelancer getBelong() {
        return belong;
    }

    public Freelance belong(Freelancer freelancer) {
        this.belong = freelancer;
        return this;
    }

    public void setBelong(Freelancer freelancer) {
        this.belong = freelancer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Freelance)) {
            return false;
        }
        return id != null && id.equals(((Freelance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Freelance{" +
            "id=" + getId() +
            "}";
    }

    public Set<Work> getExecutes() {
        return this.executes;
    }
}
