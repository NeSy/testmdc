package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.mycompany.myapp.domain.enumeration.CdmNature;

import com.mycompany.myapp.domain.enumeration.EqptKind;

/**
 * A Cdm.
 */
@Entity
@Table(name = "cdm")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cdm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = "[0-9][0-9]")
    @Column(name = "jhi_index", nullable = false)
    private String index;

    @NotNull
    @Column(name = "name_fr", nullable = false)
    private String nameFR;

    @Column(name = "name_gb")
    private String nameGB;

    @NotNull
    @Column(name = "comment_fr", nullable = false)
    private String commentFR;

    @Column(name = "comment_gb")
    private String commentGB;

    @NotNull
    @Column(name = "doc_name_fr", nullable = false)
    private String docNameFR;

    @Column(name = "doc_name_gb")
    private String docNameGB;

    @NotNull
    @Column(name = "bus_message", nullable = false)
    private String busMessage;

    @NotNull
    @Column(name = "bus_word", nullable = false)
    private String busWord;

    @NotNull
    @Column(name = "mnemonic_fr", nullable = false)
    private String mnemonicFR;

    @Column(name = "offset")
    private Integer offset;

    @Column(name = "coding")
    private String coding;

    @Column(name = "unit_msg")
    private String unitMsg;

    @Column(name = "min_msg")
    private String minMsg;

    @Column(name = "max_msg")
    private String maxMsg;

    @Enumerated(EnumType.STRING)
    @Column(name = "nature")
    private CdmNature nature;

    @Column(name = "sign")
    private Boolean sign;

    @Column(name = "cadrage_vtl")
    private String cadrageVTL;

    @Column(name = "min_value_vtl")
    private String minValueVTL;

    @Column(name = "max_value_vtl")
    private String maxValueVTL;

    @Column(name = "min_byte_vtl")
    private Integer minByteVTL;

    @Column(name = "max_byte_vtl")
    private Integer maxByteVTL;

    @Column(name = "unit_vtl")
    private String unitVTL;

    @Enumerated(EnumType.STRING)
    @Column(name = "kind")
    private EqptKind kind;

    @NotNull
    @Column(name = "jhi_linear", nullable = false)
    private Boolean linear;

    @NotNull
    @Column(name = "func", nullable = false)
    private Integer func;

    @ManyToOne
    @JsonIgnoreProperties("cdms")
    private Eqpt eqpt;

    @OneToOne
    @JoinColumn(unique = true)
    private MaintenanceConf maintenanceConfiguration;

    @OneToMany(mappedBy = "cdm")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LibCdm> libCdms = new HashSet<>();

    @OneToMany(mappedBy = "cdm")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AircraftConf> aircraftConfRefs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndex() {
        return index;
    }

    public Cdm index(String index) {
        this.index = index;
        return this;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getNameFR() {
        return nameFR;
    }

    public Cdm nameFR(String nameFR) {
        this.nameFR = nameFR;
        return this;
    }

    public void setNameFR(String nameFR) {
        this.nameFR = nameFR;
    }

    public String getNameGB() {
        return nameGB;
    }

    public Cdm nameGB(String nameGB) {
        this.nameGB = nameGB;
        return this;
    }

    public void setNameGB(String nameGB) {
        this.nameGB = nameGB;
    }

    public String getCommentFR() {
        return commentFR;
    }

    public Cdm commentFR(String commentFR) {
        this.commentFR = commentFR;
        return this;
    }

    public void setCommentFR(String commentFR) {
        this.commentFR = commentFR;
    }

    public String getCommentGB() {
        return commentGB;
    }

    public Cdm commentGB(String commentGB) {
        this.commentGB = commentGB;
        return this;
    }

    public void setCommentGB(String commentGB) {
        this.commentGB = commentGB;
    }

    public String getDocNameFR() {
        return docNameFR;
    }

    public Cdm docNameFR(String docNameFR) {
        this.docNameFR = docNameFR;
        return this;
    }

    public void setDocNameFR(String docNameFR) {
        this.docNameFR = docNameFR;
    }

    public String getDocNameGB() {
        return docNameGB;
    }

    public Cdm docNameGB(String docNameGB) {
        this.docNameGB = docNameGB;
        return this;
    }

    public void setDocNameGB(String docNameGB) {
        this.docNameGB = docNameGB;
    }

    public String getBusMessage() {
        return busMessage;
    }

    public Cdm busMessage(String busMessage) {
        this.busMessage = busMessage;
        return this;
    }

    public void setBusMessage(String busMessage) {
        this.busMessage = busMessage;
    }

    public String getBusWord() {
        return busWord;
    }

    public Cdm busWord(String busWord) {
        this.busWord = busWord;
        return this;
    }

    public void setBusWord(String busWord) {
        this.busWord = busWord;
    }

    public String getMnemonicFR() {
        return mnemonicFR;
    }

    public Cdm mnemonicFR(String mnemonicFR) {
        this.mnemonicFR = mnemonicFR;
        return this;
    }

    public void setMnemonicFR(String mnemonicFR) {
        this.mnemonicFR = mnemonicFR;
    }

    public Integer getOffset() {
        return offset;
    }

    public Cdm offset(Integer offset) {
        this.offset = offset;
        return this;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getCoding() {
        return coding;
    }

    public Cdm coding(String coding) {
        this.coding = coding;
        return this;
    }

    public void setCoding(String coding) {
        this.coding = coding;
    }

    public String getUnitMsg() {
        return unitMsg;
    }

    public Cdm unitMsg(String unitMsg) {
        this.unitMsg = unitMsg;
        return this;
    }

    public void setUnitMsg(String unitMsg) {
        this.unitMsg = unitMsg;
    }

    public String getMinMsg() {
        return minMsg;
    }

    public Cdm minMsg(String minMsg) {
        this.minMsg = minMsg;
        return this;
    }

    public void setMinMsg(String minMsg) {
        this.minMsg = minMsg;
    }

    public String getMaxMsg() {
        return maxMsg;
    }

    public Cdm maxMsg(String maxMsg) {
        this.maxMsg = maxMsg;
        return this;
    }

    public void setMaxMsg(String maxMsg) {
        this.maxMsg = maxMsg;
    }

    public CdmNature getNature() {
        return nature;
    }

    public Cdm nature(CdmNature nature) {
        this.nature = nature;
        return this;
    }

    public void setNature(CdmNature nature) {
        this.nature = nature;
    }

    public Boolean isSign() {
        return sign;
    }

    public Cdm sign(Boolean sign) {
        this.sign = sign;
        return this;
    }

    public void setSign(Boolean sign) {
        this.sign = sign;
    }

    public String getCadrageVTL() {
        return cadrageVTL;
    }

    public Cdm cadrageVTL(String cadrageVTL) {
        this.cadrageVTL = cadrageVTL;
        return this;
    }

    public void setCadrageVTL(String cadrageVTL) {
        this.cadrageVTL = cadrageVTL;
    }

    public String getMinValueVTL() {
        return minValueVTL;
    }

    public Cdm minValueVTL(String minValueVTL) {
        this.minValueVTL = minValueVTL;
        return this;
    }

    public void setMinValueVTL(String minValueVTL) {
        this.minValueVTL = minValueVTL;
    }

    public String getMaxValueVTL() {
        return maxValueVTL;
    }

    public Cdm maxValueVTL(String maxValueVTL) {
        this.maxValueVTL = maxValueVTL;
        return this;
    }

    public void setMaxValueVTL(String maxValueVTL) {
        this.maxValueVTL = maxValueVTL;
    }

    public Integer getMinByteVTL() {
        return minByteVTL;
    }

    public Cdm minByteVTL(Integer minByteVTL) {
        this.minByteVTL = minByteVTL;
        return this;
    }

    public void setMinByteVTL(Integer minByteVTL) {
        this.minByteVTL = minByteVTL;
    }

    public Integer getMaxByteVTL() {
        return maxByteVTL;
    }

    public Cdm maxByteVTL(Integer maxByteVTL) {
        this.maxByteVTL = maxByteVTL;
        return this;
    }

    public void setMaxByteVTL(Integer maxByteVTL) {
        this.maxByteVTL = maxByteVTL;
    }

    public String getUnitVTL() {
        return unitVTL;
    }

    public Cdm unitVTL(String unitVTL) {
        this.unitVTL = unitVTL;
        return this;
    }

    public void setUnitVTL(String unitVTL) {
        this.unitVTL = unitVTL;
    }

    public EqptKind getKind() {
        return kind;
    }

    public Cdm kind(EqptKind kind) {
        this.kind = kind;
        return this;
    }

    public void setKind(EqptKind kind) {
        this.kind = kind;
    }

    public Boolean isLinear() {
        return linear;
    }

    public Cdm linear(Boolean linear) {
        this.linear = linear;
        return this;
    }

    public void setLinear(Boolean linear) {
        this.linear = linear;
    }

    public Integer getFunc() {
        return func;
    }

    public Cdm func(Integer func) {
        this.func = func;
        return this;
    }

    public void setFunc(Integer func) {
        this.func = func;
    }

    public Eqpt getEqpt() {
        return eqpt;
    }

    public Cdm eqpt(Eqpt eqpt) {
        this.eqpt = eqpt;
        return this;
    }

    public void setEqpt(Eqpt eqpt) {
        this.eqpt = eqpt;
    }

    public MaintenanceConf getMaintenanceConfiguration() {
        return maintenanceConfiguration;
    }

    public Cdm maintenanceConfiguration(MaintenanceConf maintenanceConf) {
        this.maintenanceConfiguration = maintenanceConf;
        return this;
    }

    public void setMaintenanceConfiguration(MaintenanceConf maintenanceConf) {
        this.maintenanceConfiguration = maintenanceConf;
    }

    public Set<LibCdm> getLibCdms() {
        return libCdms;
    }

    public Cdm libCdms(Set<LibCdm> libCdms) {
        this.libCdms = libCdms;
        return this;
    }

    public Cdm addLibCdm(LibCdm libCdm) {
        this.libCdms.add(libCdm);
        libCdm.setCdm(this);
        return this;
    }

    public Cdm removeLibCdm(LibCdm libCdm) {
        this.libCdms.remove(libCdm);
        libCdm.setCdm(null);
        return this;
    }

    public void setLibCdms(Set<LibCdm> libCdms) {
        this.libCdms = libCdms;
    }

    public Set<AircraftConf> getAircraftConfRefs() {
        return aircraftConfRefs;
    }

    public Cdm aircraftConfRefs(Set<AircraftConf> aircraftConfs) {
        this.aircraftConfRefs = aircraftConfs;
        return this;
    }

    public Cdm addAircraftConfRef(AircraftConf aircraftConf) {
        this.aircraftConfRefs.add(aircraftConf);
        aircraftConf.setCdm(this);
        return this;
    }

    public Cdm removeAircraftConfRef(AircraftConf aircraftConf) {
        this.aircraftConfRefs.remove(aircraftConf);
        aircraftConf.setCdm(null);
        return this;
    }

    public void setAircraftConfRefs(Set<AircraftConf> aircraftConfs) {
        this.aircraftConfRefs = aircraftConfs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cdm)) {
            return false;
        }
        return id != null && id.equals(((Cdm) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Cdm{" +
            "id=" + getId() +
            ", index='" + getIndex() + "'" +
            ", nameFR='" + getNameFR() + "'" +
            ", nameGB='" + getNameGB() + "'" +
            ", commentFR='" + getCommentFR() + "'" +
            ", commentGB='" + getCommentGB() + "'" +
            ", docNameFR='" + getDocNameFR() + "'" +
            ", docNameGB='" + getDocNameGB() + "'" +
            ", busMessage='" + getBusMessage() + "'" +
            ", busWord='" + getBusWord() + "'" +
            ", mnemonicFR='" + getMnemonicFR() + "'" +
            ", offset=" + getOffset() +
            ", coding='" + getCoding() + "'" +
            ", unitMsg='" + getUnitMsg() + "'" +
            ", minMsg='" + getMinMsg() + "'" +
            ", maxMsg='" + getMaxMsg() + "'" +
            ", nature='" + getNature() + "'" +
            ", sign='" + isSign() + "'" +
            ", cadrageVTL='" + getCadrageVTL() + "'" +
            ", minValueVTL='" + getMinValueVTL() + "'" +
            ", maxValueVTL='" + getMaxValueVTL() + "'" +
            ", minByteVTL=" + getMinByteVTL() +
            ", maxByteVTL=" + getMaxByteVTL() +
            ", unitVTL='" + getUnitVTL() + "'" +
            ", kind='" + getKind() + "'" +
            ", linear='" + isLinear() + "'" +
            ", func=" + getFunc() +
            "}";
    }
}
