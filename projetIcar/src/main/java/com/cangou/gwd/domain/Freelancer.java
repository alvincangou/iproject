package com.cangou.gwd.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;
@Entity
@DiscriminatorValue(value = "FREELANCER")
public class Freelancer extends User {



    private String country_code;

    private Date creationDate;

    @OneToMany(mappedBy="freelancer")
    private List<Freelance> freelances ;



    public List<Freelance> getFreelances() {
        return freelances;
    }

    public void setFreelances(List<Freelance> freelances) {
        this.freelances = freelances;
    }




    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
