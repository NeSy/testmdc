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
 * A Bi.
 */
@Entity
@Table(name = "bi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Bi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @Column(name = "mnemonic_fr", nullable = false)
    private String mnemonicFR;

    @Column(name = "mnemonic_gb")
    private String mnemonicGB;

    @Column(name = "cdp")
    private String cdp;

    @ManyToOne
    @JsonIgnoreProperties("bis")
    private Eqpt eqpt;

    @OneToMany(mappedBy = "bi")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Td> tds = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameFR() {
        return nameFR;
    }

    public Bi nameFR(String nameFR) {
        this.nameFR = nameFR;
        return this;
    }

    public void setNameFR(String nameFR) {
        this.nameFR = nameFR;
    }

    public String getNameGB() {
        return nameGB;
    }

    public Bi nameGB(String nameGB) {
        this.nameGB = nameGB;
        return this;
    }

    public void setNameGB(String nameGB) {
        this.nameGB = nameGB;
    }

    public String getCommentFR() {
        return commentFR;
    }

    public Bi commentFR(String commentFR) {
        this.commentFR = commentFR;
        return this;
    }

    public void setCommentFR(String commentFR) {
        this.commentFR = commentFR;
    }

    public String getCommentGB() {
        return commentGB;
    }

    public Bi commentGB(String commentGB) {
        this.commentGB = commentGB;
        return this;
    }

    public void setCommentGB(String commentGB) {
        this.commentGB = commentGB;
    }

    public String getMnemonicFR() {
        return mnemonicFR;
    }

    public Bi mnemonicFR(String mnemonicFR) {
        this.mnemonicFR = mnemonicFR;
        return this;
    }

    public void setMnemonicFR(String mnemonicFR) {
        this.mnemonicFR = mnemonicFR;
    }

    public String getMnemonicGB() {
        return mnemonicGB;
    }

    public Bi mnemonicGB(String mnemonicGB) {
        this.mnemonicGB = mnemonicGB;
        return this;
    }

    public void setMnemonicGB(String mnemonicGB) {
        this.mnemonicGB = mnemonicGB;
    }

    public String getCdp() {
        return cdp;
    }

    public Bi cdp(String cdp) {
        this.cdp = cdp;
        return this;
    }

    public void setCdp(String cdp) {
        this.cdp = cdp;
    }

    public Eqpt getEqpt() {
        return eqpt;
    }

    public Bi eqpt(Eqpt eqpt) {
        this.eqpt = eqpt;
        return this;
    }

    public void setEqpt(Eqpt eqpt) {
        this.eqpt = eqpt;
    }

    public Set<Td> getTds() {
        return tds;
    }

    public Bi tds(Set<Td> tds) {
        this.tds = tds;
        return this;
    }

    public Bi addTd(Td td) {
        this.tds.add(td);
        td.setBi(this);
        return this;
    }

    public Bi removeTd(Td td) {
        this.tds.remove(td);
        td.setBi(null);
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
        if (!(o instanceof Bi)) {
            return false;
        }
        return id != null && id.equals(((Bi) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Bi{" +
            "id=" + getId() +
            ", nameFR='" + getNameFR() + "'" +
            ", nameGB='" + getNameGB() + "'" +
            ", commentFR='" + getCommentFR() + "'" +
            ", commentGB='" + getCommentGB() + "'" +
            ", mnemonicFR='" + getMnemonicFR() + "'" +
            ", mnemonicGB='" + getMnemonicGB() + "'" +
            ", cdp='" + getCdp() + "'" +
            "}";
    }
}
