package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A LibCdm.
 */
@Entity
@Table(name = "lib_cdm")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LibCdm implements Serializable {

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
    @Column(name = "doc_name_fr", nullable = false)
    private String docNameFR;

    @Column(name = "doc_name_gb")
    private String docNameGB;

    @ManyToOne
    @JsonIgnoreProperties("libCdms")
    private Cdm cdm;

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

    public LibCdm nameFR(String nameFR) {
        this.nameFR = nameFR;
        return this;
    }

    public void setNameFR(String nameFR) {
        this.nameFR = nameFR;
    }

    public String getNameGB() {
        return nameGB;
    }

    public LibCdm nameGB(String nameGB) {
        this.nameGB = nameGB;
        return this;
    }

    public void setNameGB(String nameGB) {
        this.nameGB = nameGB;
    }

    public String getCommentFR() {
        return commentFR;
    }

    public LibCdm commentFR(String commentFR) {
        this.commentFR = commentFR;
        return this;
    }

    public void setCommentFR(String commentFR) {
        this.commentFR = commentFR;
    }

    public String getCommentGB() {
        return commentGB;
    }

    public LibCdm commentGB(String commentGB) {
        this.commentGB = commentGB;
        return this;
    }

    public void setCommentGB(String commentGB) {
        this.commentGB = commentGB;
    }

    public String getDocNameFR() {
        return docNameFR;
    }

    public LibCdm docNameFR(String docNameFR) {
        this.docNameFR = docNameFR;
        return this;
    }

    public void setDocNameFR(String docNameFR) {
        this.docNameFR = docNameFR;
    }

    public String getDocNameGB() {
        return docNameGB;
    }

    public LibCdm docNameGB(String docNameGB) {
        this.docNameGB = docNameGB;
        return this;
    }

    public void setDocNameGB(String docNameGB) {
        this.docNameGB = docNameGB;
    }

    public Cdm getCdm() {
        return cdm;
    }

    public LibCdm cdm(Cdm cdm) {
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
        if (!(o instanceof LibCdm)) {
            return false;
        }
        return id != null && id.equals(((LibCdm) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LibCdm{" +
            "id=" + getId() +
            ", nameFR='" + getNameFR() + "'" +
            ", nameGB='" + getNameGB() + "'" +
            ", commentFR='" + getCommentFR() + "'" +
            ", commentGB='" + getCommentGB() + "'" +
            ", docNameFR='" + getDocNameFR() + "'" +
            ", docNameGB='" + getDocNameGB() + "'" +
            "}";
    }
}
