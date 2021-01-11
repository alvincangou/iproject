package com.cangou.gwd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Service.
 */
@Entity
@Table(name = "service")
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "necessary_time")
    private Double necessaryTime;

    @Column(name = "price")
    private String price;

    @ManyToOne
    @JsonIgnoreProperties(value = "services", allowSetters = true)
    private Freelance ability;

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

    public Service name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getNecessaryTime() {
        return necessaryTime;
    }

    public Service necessaryTime(Double necessaryTime) {
        this.necessaryTime = necessaryTime;
        return this;
    }

    public void setNecessaryTime(Double necessaryTime) {
        this.necessaryTime = necessaryTime;
    }

    public String getPrice() {
        return price;
    }

    public Service price(String price) {
        this.price = price;
        return this;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Freelance getAbility() {
        return ability;
    }

    public Service ability(Freelance freelance) {
        this.ability = freelance;
        return this;
    }

    public void setAbility(Freelance freelance) {
        this.ability = freelance;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Service)) {
            return false;
        }
        return id != null && id.equals(((Service) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Service{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", necessaryTime=" + getNecessaryTime() +
            ", price='" + getPrice() + "'" +
            "}";
    }
}
