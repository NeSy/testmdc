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
 * A PointEmport.
 */
@Entity
@Table(name = "point_emport")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PointEmport implements Serializable {

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

    @Column(name = "mnemonic_gb")
    private String mnemonicGB;

    @ManyToOne
    @JsonIgnoreProperties("pointEmports")
    private DicoCDM dicoCDM;

    @OneToMany(mappedBy = "pointEmport")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Eqpt> eqpts = new HashSet<>();

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

    public PointEmport nameFR(String nameFR) {
        this.nameFR = nameFR;
        return this;
    }

    public void setNameFR(String nameFR) {
        this.nameFR = nameFR;
    }

    public String getNameGB() {
        return nameGB;
    }

    public PointEmport nameGB(String nameGB) {
        this.nameGB = nameGB;
        return this;
    }

    public void setNameGB(String nameGB) {
        this.nameGB = nameGB;
    }

    public String getCommentFR() {
        return commentFR;
    }

    public PointEmport commentFR(String commentFR) {
        this.commentFR = commentFR;
        return this;
    }

    public void setCommentFR(String commentFR) {
        this.commentFR = commentFR;
    }

    public String getCommentGB() {
        return commentGB;
    }

    public PointEmport commentGB(String commentGB) {
        this.commentGB = commentGB;
        return this;
    }

    public void setCommentGB(String commentGB) {
        this.commentGB = commentGB;
    }

    public String getMnemonicGB() {
        return mnemonicGB;
    }

    public PointEmport mnemonicGB(String mnemonicGB) {
        this.mnemonicGB = mnemonicGB;
        return this;
    }

    public void setMnemonicGB(String mnemonicGB) {
        this.mnemonicGB = mnemonicGB;
    }

    public DicoCDM getDicoCDM() {
        return dicoCDM;
    }

    public PointEmport dicoCDM(DicoCDM dicoCDM) {
        this.dicoCDM = dicoCDM;
        return this;
    }

    public void setDicoCDM(DicoCDM dicoCDM) {
        this.dicoCDM = dicoCDM;
    }

    public Set<Eqpt> getEqpts() {
        return eqpts;
    }

    public PointEmport eqpts(Set<Eqpt> eqpts) {
        this.eqpts = eqpts;
        return this;
    }

    public PointEmport addEqpt(Eqpt eqpt) {
        this.eqpts.add(eqpt);
        eqpt.setPointEmport(this);
        return this;
    }

    public PointEmport removeEqpt(Eqpt eqpt) {
        this.eqpts.remove(eqpt);
        eqpt.setPointEmport(null);
        return this;
    }

    public void setEqpts(Set<Eqpt> eqpts) {
        this.eqpts = eqpts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PointEmport)) {
            return false;
        }
        return id != null && id.equals(((PointEmport) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PointEmport{" +
            "id=" + getId() +
            ", nameFR='" + getNameFR() + "'" +
            ", nameGB='" + getNameGB() + "'" +
            ", commentFR='" + getCommentFR() + "'" +
            ", commentGB='" + getCommentGB() + "'" +
            ", mnemonicGB='" + getMnemonicGB() + "'" +
            "}";
    }
}
