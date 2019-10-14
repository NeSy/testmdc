package com.mycompany.myapp.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DicoCDM.
 */
@Entity
@Table(name = "dico_cdm")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DicoCDM implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "prev_dico_id")
    private String prevDicoId;

    @Column(name = "imt_ref")
    private String imtRef;

    @NotNull
    @Column(name = "version", nullable = false)
    private String version;

    @NotNull
    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "release_date")
    private String releaseDate;

    @Column(name = "tool_version")
    private String toolVersion;

    @NotNull
    @Column(name = "state", nullable = false)
    private String state;

    @OneToMany(mappedBy = "dicoCDM")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PointEmport> pointEmports = new HashSet<>();

    @OneToMany(mappedBy = "dicoCDM")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Eqpt> sas = new HashSet<>();

    @OneToMany(mappedBy = "dicoCDM")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AircraftConf> aircraftConfs = new HashSet<>();

    @OneToMany(mappedBy = "dicoCDM")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MaintenanceConf> maintenanceConfs = new HashSet<>();

    @OneToMany(mappedBy = "dicoCDM")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MaintenanceConfCombination> maintenaceConfCombinations = new HashSet<>();

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

    public DicoCDM name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrevDicoId() {
        return prevDicoId;
    }

    public DicoCDM prevDicoId(String prevDicoId) {
        this.prevDicoId = prevDicoId;
        return this;
    }

    public void setPrevDicoId(String prevDicoId) {
        this.prevDicoId = prevDicoId;
    }

    public String getImtRef() {
        return imtRef;
    }

    public DicoCDM imtRef(String imtRef) {
        this.imtRef = imtRef;
        return this;
    }

    public void setImtRef(String imtRef) {
        this.imtRef = imtRef;
    }

    public String getVersion() {
        return version;
    }

    public DicoCDM version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDate() {
        return date;
    }

    public DicoCDM date(String date) {
        this.date = date;
        return this;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public DicoCDM releaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getToolVersion() {
        return toolVersion;
    }

    public DicoCDM toolVersion(String toolVersion) {
        this.toolVersion = toolVersion;
        return this;
    }

    public void setToolVersion(String toolVersion) {
        this.toolVersion = toolVersion;
    }

    public String getState() {
        return state;
    }

    public DicoCDM state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Set<PointEmport> getPointEmports() {
        return pointEmports;
    }

    public DicoCDM pointEmports(Set<PointEmport> pointEmports) {
        this.pointEmports = pointEmports;
        return this;
    }

    public DicoCDM addPointEmport(PointEmport pointEmport) {
        this.pointEmports.add(pointEmport);
        pointEmport.setDicoCDM(this);
        return this;
    }

    public DicoCDM removePointEmport(PointEmport pointEmport) {
        this.pointEmports.remove(pointEmport);
        pointEmport.setDicoCDM(null);
        return this;
    }

    public void setPointEmports(Set<PointEmport> pointEmports) {
        this.pointEmports = pointEmports;
    }

    public Set<Eqpt> getSas() {
        return sas;
    }

    public DicoCDM sas(Set<Eqpt> eqpts) {
        this.sas = eqpts;
        return this;
    }

    public DicoCDM addSa(Eqpt eqpt) {
        this.sas.add(eqpt);
        eqpt.setDicoCDM(this);
        return this;
    }

    public DicoCDM removeSa(Eqpt eqpt) {
        this.sas.remove(eqpt);
        eqpt.setDicoCDM(null);
        return this;
    }

    public void setSas(Set<Eqpt> eqpts) {
        this.sas = eqpts;
    }

    public Set<AircraftConf> getAircraftConfs() {
        return aircraftConfs;
    }

    public DicoCDM aircraftConfs(Set<AircraftConf> aircraftConfs) {
        this.aircraftConfs = aircraftConfs;
        return this;
    }

    public DicoCDM addAircraftConf(AircraftConf aircraftConf) {
        this.aircraftConfs.add(aircraftConf);
        aircraftConf.setDicoCDM(this);
        return this;
    }

    public DicoCDM removeAircraftConf(AircraftConf aircraftConf) {
        this.aircraftConfs.remove(aircraftConf);
        aircraftConf.setDicoCDM(null);
        return this;
    }

    public void setAircraftConfs(Set<AircraftConf> aircraftConfs) {
        this.aircraftConfs = aircraftConfs;
    }

    public Set<MaintenanceConf> getMaintenanceConfs() {
        return maintenanceConfs;
    }

    public DicoCDM maintenanceConfs(Set<MaintenanceConf> maintenanceConfs) {
        this.maintenanceConfs = maintenanceConfs;
        return this;
    }

    public DicoCDM addMaintenanceConf(MaintenanceConf maintenanceConf) {
        this.maintenanceConfs.add(maintenanceConf);
        maintenanceConf.setDicoCDM(this);
        return this;
    }

    public DicoCDM removeMaintenanceConf(MaintenanceConf maintenanceConf) {
        this.maintenanceConfs.remove(maintenanceConf);
        maintenanceConf.setDicoCDM(null);
        return this;
    }

    public void setMaintenanceConfs(Set<MaintenanceConf> maintenanceConfs) {
        this.maintenanceConfs = maintenanceConfs;
    }

    public Set<MaintenanceConfCombination> getMaintenaceConfCombinations() {
        return maintenaceConfCombinations;
    }

    public DicoCDM maintenaceConfCombinations(Set<MaintenanceConfCombination> maintenanceConfCombinations) {
        this.maintenaceConfCombinations = maintenanceConfCombinations;
        return this;
    }

    public DicoCDM addMaintenaceConfCombination(MaintenanceConfCombination maintenanceConfCombination) {
        this.maintenaceConfCombinations.add(maintenanceConfCombination);
        maintenanceConfCombination.setDicoCDM(this);
        return this;
    }

    public DicoCDM removeMaintenaceConfCombination(MaintenanceConfCombination maintenanceConfCombination) {
        this.maintenaceConfCombinations.remove(maintenanceConfCombination);
        maintenanceConfCombination.setDicoCDM(null);
        return this;
    }

    public void setMaintenaceConfCombinations(Set<MaintenanceConfCombination> maintenanceConfCombinations) {
        this.maintenaceConfCombinations = maintenanceConfCombinations;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DicoCDM)) {
            return false;
        }
        return id != null && id.equals(((DicoCDM) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DicoCDM{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", prevDicoId='" + getPrevDicoId() + "'" +
            ", imtRef='" + getImtRef() + "'" +
            ", version='" + getVersion() + "'" +
            ", date='" + getDate() + "'" +
            ", releaseDate='" + getReleaseDate() + "'" +
            ", toolVersion='" + getToolVersion() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
