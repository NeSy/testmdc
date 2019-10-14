package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Cdm;
import com.mycompany.myapp.repository.CdmRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Cdm}.
 */
@RestController
@RequestMapping("/api")
public class CdmResource {

    private final Logger log = LoggerFactory.getLogger(CdmResource.class);

    private static final String ENTITY_NAME = "cdm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CdmRepository cdmRepository;

    public CdmResource(CdmRepository cdmRepository) {
        this.cdmRepository = cdmRepository;
    }

    /**
     * {@code POST  /cdms} : Create a new cdm.
     *
     * @param cdm the cdm to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cdm, or with status {@code 400 (Bad Request)} if the cdm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cdms")
    public ResponseEntity<Cdm> createCdm(@Valid @RequestBody Cdm cdm) throws URISyntaxException {
        log.debug("REST request to save Cdm : {}", cdm);
        if (cdm.getId() != null) {
            throw new BadRequestAlertException("A new cdm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cdm result = cdmRepository.save(cdm);
        return ResponseEntity.created(new URI("/api/cdms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cdms} : Updates an existing cdm.
     *
     * @param cdm the cdm to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cdm,
     * or with status {@code 400 (Bad Request)} if the cdm is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cdm couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cdms")
    public ResponseEntity<Cdm> updateCdm(@Valid @RequestBody Cdm cdm) throws URISyntaxException {
        log.debug("REST request to update Cdm : {}", cdm);
        if (cdm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Cdm result = cdmRepository.save(cdm);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cdm.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cdms} : get all the cdms.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cdms in body.
     */
    @GetMapping("/cdms")
    public List<Cdm> getAllCdms() {
        log.debug("REST request to get all Cdms");
        return cdmRepository.findAll();
    }

    /**
     * {@code GET  /cdms/:id} : get the "id" cdm.
     *
     * @param id the id of the cdm to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cdm, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cdms/{id}")
    public ResponseEntity<Cdm> getCdm(@PathVariable Long id) {
        log.debug("REST request to get Cdm : {}", id);
        Optional<Cdm> cdm = cdmRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cdm);
    }

    /**
     * {@code DELETE  /cdms/:id} : delete the "id" cdm.
     *
     * @param id the id of the cdm to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cdms/{id}")
    public ResponseEntity<Void> deleteCdm(@PathVariable Long id) {
        log.debug("REST request to delete Cdm : {}", id);
        cdmRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
