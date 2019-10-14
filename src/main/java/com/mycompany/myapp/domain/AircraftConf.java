package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A AircraftConf.
 */
@Entity
@Table(name = "aircraft_conf")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AircraftConf implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JsonIgnoreProperties("aircraftConfs")
    private DicoCDM dicoCDM;

    @ManyToOne
    @JsonIgnoreProperties("aircraftConfs")
    private Cdm cdm;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public AircraftConf name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DicoCDM getDicoCDM() {
        return dicoCDM;
    }

    public AircraftConf dicoCDM(DicoCDM dicoCDM) {
        this.dicoCDM = dicoCDM;
        return this;
    }

    public void setDicoCDM(DicoCDM dicoCDM) {
        this.dicoCDM = dicoCDM;
    }

    public Cdm getCdm() {
        return cdm;
    }

    public AircraftConf cdm(Cdm cdm) {
        this.cdm = cdm;
        return this;
    }

    public void setCdm(Cdm cdm) {
        this.cdm = cdm;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AircraftConf)) {
            return false;
        }
        return id != null && id.equals(((AircraftConf) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AircraftConf{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
