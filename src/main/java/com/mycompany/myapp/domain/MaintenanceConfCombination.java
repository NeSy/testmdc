package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A MaintenanceConfCombination.
 */
@Entity
@Table(name = "maintenance_conf_combination")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MaintenanceConfCombination implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JsonIgnoreProperties("maintenanceConfCombinations")
    private DicoCDM dicoCDM;

    @OneToMany(mappedBy = "maintenanceConfCombination")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MaintenanceConf> maintenanceConfRefs = new HashSet<>();

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

    public MaintenanceConfCombination name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DicoCDM getDicoCDM() {
        return dicoCDM;
    }

    public MaintenanceConfCombination dicoCDM(DicoCDM dicoCDM) {
        this.dicoCDM = dicoCDM;
        return this;
    }

    public void setDicoCDM(DicoCDM dicoCDM) {
        this.dicoCDM = dicoCDM;
    }

    public Set<MaintenanceConf> getMaintenanceConfRefs() {
        return maintenanceConfRefs;
    }

    public MaintenanceConfCombination maintenanceConfRefs(Set<MaintenanceConf> maintenanceConfs) {
        this.maintenanceConfRefs = maintenanceConfs;
        return this;
    }

    public MaintenanceConfCombination addMaintenanceConfRef(MaintenanceConf maintenanceConf) {
        this.maintenanceConfRefs.add(maintenanceConf);
        maintenanceConf.setMaintenanceConfCombination(this);
        return this;
    }

    public MaintenanceConfCombination removeMaintenanceConfRef(MaintenanceConf maintenanceConf) {
        this.maintenanceConfRefs.remove(maintenanceConf);
        maintenanceConf.setMaintenanceConfCombination(null);
        return this;
    }

    public void setMaintenanceConfRefs(Set<MaintenanceConf> maintenanceConfs) {
        this.maintenanceConfRefs = maintenanceConfs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MaintenanceConfCombination)) {
            return false;
        }
        return id != null && id.equals(((MaintenanceConfCombination) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MaintenanceConfCombination{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
