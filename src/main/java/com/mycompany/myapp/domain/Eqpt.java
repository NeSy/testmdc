package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.mycompany.myapp.domain.enumeration.EqptType;

/**
 * A Eqpt.
 */
@Entity
@Table(name = "eqpt")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Eqpt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private EqptType type;

    @NotNull
    @Column(name = "name_fr", nullable = false)
    private String nameFR;

    @Column(name = "name_gb")
    private String nameGB;

    @NotNull
    @Column(name = "mnemonic_fr", nullable = false)
    private String mnemonicFR;

    @Column(name = "mnemonic_gb")
    private String mnemonicGB;

    @NotNull
    @Column(name = "comment_fr", nullable = false)
    private String commentFR;

    @Column(name = "comment_gb")
    private String commentGB;

    @NotNull
    @Pattern(regexp = "[0-9][0-9]")
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull
    @Column(name = "kind", nullable = false)
    private String kind;

    @Column(name = "cdp")
    private String cdp;

    @ManyToOne
    @JsonIgnoreProperties("eqpts")
    private DicoCDM dicoCDM;

    @ManyToOne
    @JsonIgnoreProperties("eqpts")
    private PointEmport pointEmport;

    @OneToOne
    @JoinColumn(unique = true)
    private Bus busRef;

    @OneToMany(mappedBy = "eqpt")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Cdm> cdms = new HashSet<>();

    @OneToMany(mappedBy = "eqpt")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Bi> bis = new HashSet<>();

    @OneToMany(mappedBy = "eqpt")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Td> tds = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EqptType getType() {
        return type;
    }

    public Eqpt type(EqptType type) {
        this.type = type;
        return this;
    }

    public void setType(EqptType type) {
        this.type = type;
    }

    public String getNameFR() {
        return nameFR;
    }

    public Eqpt nameFR(String nameFR) {
        this.nameFR = nameFR;
        return this;
    }

    public void setNameFR(String nameFR) {
        this.nameFR = nameFR;
    }

    public String getNameGB() {
        return nameGB;
    }

    public Eqpt nameGB(String nameGB) {
        this.nameGB = nameGB;
        return this;
    }

    public void setNameGB(String nameGB) {
        this.nameGB = nameGB;
    }

    public String getMnemonicFR() {
        return mnemonicFR;
    }

    public Eqpt mnemonicFR(String mnemonicFR) {
        this.mnemonicFR = mnemonicFR;
        return this;
    }

    public void setMnemonicFR(String mnemonicFR) {
        this.mnemonicFR = mnemonicFR;
    }

    public String getMnemonicGB() {
        return mnemonicGB;
    }

    public Eqpt mnemonicGB(String mnemonicGB) {
        this.mnemonicGB = mnemonicGB;
        return this;
    }

    public void setMnemonicGB(String mnemonicGB) {
        this.mnemonicGB = mnemonicGB;
    }

    public String getCommentFR() {
        return commentFR;
    }

    public Eqpt commentFR(String commentFR) {
        this.commentFR = commentFR;
        return this;
    }

    public void setCommentFR(String commentFR) {
        this.commentFR = commentFR;
    }

    public String getCommentGB() {
        return commentGB;
    }

    public Eqpt commentGB(String commentGB) {
        this.commentGB = commentGB;
        return this;
    }

    public void setCommentGB(String commentGB) {
        this.commentGB = commentGB;
    }

    public String getAddress() {
        return address;
    }

    public Eqpt address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getKind() {
        return kind;
    }

    public Eqpt kind(String kind) {
        this.kind = kind;
        return this;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getCdp() {
        return cdp;
    }

    public Eqpt cdp(String cdp) {
        this.cdp = cdp;
        return this;
    }

    public void setCdp(String cdp) {
        this.cdp = cdp;
    }

    public DicoCDM getDicoCDM() {
        return dicoCDM;
    }

    public Eqpt dicoCDM(DicoCDM dicoCDM) {
        this.dicoCDM = dicoCDM;
        return this;
    }

    public void setDicoCDM(DicoCDM dicoCDM) {
        this.dicoCDM = dicoCDM;
    }

    public PointEmport getPointEmport() {
        return pointEmport;
    }

    public Eqpt pointEmport(PointEmport pointEmport) {
        this.pointEmport = pointEmport;
        return this;
    }

    public void setPointEmport(PointEmport pointEmport) {
        this.pointEmport = pointEmport;
    }

    public Bus getBusRef() {
        return busRef;
    }

    public Eqpt busRef(Bus bus) {
        this.busRef = bus;
        return this;
    }

    public void setBusRef(Bus bus) {
        this.busRef = bus;
    }

    public Set<Cdm> getCdms() {
        return cdms;
    }

    public Eqpt cdms(Set<Cdm> cdms) {
        this.cdms = cdms;
        return this;
    }

    public Eqpt addCdm(Cdm cdm) {
        this.cdms.add(cdm);
        cdm.setEqpt(this);
        return this;
    }

    public Eqpt removeCdm(Cdm cdm) {
        this.cdms.remove(cdm);
        cdm.setEqpt(null);
        return this;
    }

    public void setCdms(Set<Cdm> cdms) {
        this.cdms = cdms;
    }

    public Set<Bi> getBis() {
        return bis;
    }

    public Eqpt bis(Set<Bi> bis) {
        this.bis = bis;
        return this;
    }

    public Eqpt addBi(Bi bi) {
        this.bis.add(bi);
        bi.setEqpt(this);
        return this;
    }

    public Eqpt removeBi(Bi bi) {
        this.bis.remove(bi);
        bi.setEqpt(null);
        return this;
    }

    public void setBis(Set<Bi> bis) {
        this.bis = bis;
    }

    public Set<Td> getTds() {
        return tds;
    }

    public Eqpt tds(Set<Td> tds) {
        this.tds = tds;
        return this;
    }

    public Eqpt addTd(Td td) {
        this.tds.add(td);
        td.setEqpt(this);
        return this;
    }

    public Eqpt removeTd(Td td) {
        this.tds.remove(td);
        td.setEqpt(null);
        return this;
    }

    public void setTds(Set<Td> tds) {
        this.tds = tds;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Eqpt)) {
            return false;
        }
        return id != null && id.equals(((Eqpt) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Eqpt{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", nameFR='" + getNameFR() + "'" +
            ", nameGB='" + getNameGB() + "'" +
            ", mnemonicFR='" + getMnemonicFR() + "'" +
            ", mnemonicGB='" + getMnemonicGB() + "'" +
            ", commentFR='" + getCommentFR() + "'" +
            ", commentGB='" + getCommentGB() + "'" +
            ", address='" + getAddress() + "'" +
            ", kind='" + getKind() + "'" +
            ", cdp='" + getCdp() + "'" +
            "}";
    }
}
