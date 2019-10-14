package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Td.
 */
@Entity
@Table(name = "td")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Td implements Serializable {

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
    @Column(name = "number", nullable = false)
    private String number;

    @ManyToOne
    @JsonIgnoreProperties("tds")
    private Eqpt eqpt;

    @ManyToOne
    @JsonIgnoreProperties("tds")
    private Bi bi;

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

    public Td nameFR(String nameFR) {
        this.nameFR = nameFR;
        return this;
    }

    public void setNameFR(String nameFR) {
        this.nameFR = nameFR;
    }

    public String getNameGB() {
        return nameGB;
    }

    public Td nameGB(String nameGB) {
        this.nameGB = nameGB;
        return this;
    }

    public void setNameGB(String nameGB) {
        this.nameGB = nameGB;
    }

    public String getCommentFR() {
        return commentFR;
    }

    public Td commentFR(String commentFR) {
        this.commentFR = commentFR;
        return this;
    }

    public void setCommentFR(String commentFR) {
        this.commentFR = commentFR;
    }

    public String getCommentGB() {
        return commentGB;
    }

    public Td commentGB(String commentGB) {
        this.commentGB = commentGB;
        return this;
    }

    public void setCommentGB(String commentGB) {
        this.commentGB = commentGB;
    }

    public String getNumber() {
        return number;
    }

    public Td number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Eqpt getEqpt() {
        return eqpt;
    }

    public Td eqpt(Eqpt eqpt) {
        this.eqpt = eqpt;
        return this;
    }

    public void setEqpt(Eqpt eqpt) {
        this.eqpt = eqpt;
    }

    public Bi getBi() {
        return bi;
    }

    public Td bi(Bi bi) {
        this.bi = bi;
        return this;
    }

    public void setBi(Bi bi) {
        this.bi = bi;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Td)) {
            return false;
        }
        return id != null && id.equals(((Td) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Td{" +
            "id=" + getId() +
            ", nameFR='" + getNameFR() + "'" +
            ", nameGB='" + getNameGB() + "'" +
            ", commentFR='" + getCommentFR() + "'" +
            ", commentGB='" + getCommentGB() + "'" +
            ", number='" + getNumber() + "'" +
            "}";
    }
}
