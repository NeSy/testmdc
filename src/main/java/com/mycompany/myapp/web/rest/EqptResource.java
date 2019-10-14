package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Eqpt;
import com.mycompany.myapp.repository.EqptRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Eqpt}.
 */
@RestController
@RequestMapping("/api")
public class EqptResource {

    private final Logger log = LoggerFactory.getLogger(EqptResource.class);

    private static final String ENTITY_NAME = "eqpt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EqptRepository eqptRepository;

    public EqptResource(EqptRepository eqptRepository) {
        this.eqptRepository = eqptRepository;
    }

    /**
     * {@code POST  /eqpts} : Create a new eqpt.
     *
     * @param eqpt the eqpt to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eqpt, or with status {@code 400 (Bad Request)} if the eqpt has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/eqpts")
    public ResponseEntity<Eqpt> createEqpt(@Valid @RequestBody Eqpt eqpt) throws URISyntaxException {
        log.debug("REST request to save Eqpt : {}", eqpt);
        if (eqpt.getId() != null) {
            throw new BadRequestAlertException("A new eqpt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Eqpt result = eqptRepository.save(eqpt);
        return ResponseEntity.created(new URI("/api/eqpts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /eqpts} : Updates an existing eqpt.
     *
     * @param eqpt the eqpt to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eqpt,
     * or with status {@code 400 (Bad Request)} if the eqpt is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eqpt couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/eqpts")
    public ResponseEntity<Eqpt> updateEqpt(@Valid @RequestBody Eqpt eqpt) throws URISyntaxException {
        log.debug("REST request to update Eqpt : {}", eqpt);
        if (eqpt.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Eqpt result = eqptRepository.save(eqpt);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eqpt.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /eqpts} : get all the eqpts.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eqpts in body.
     */
    @GetMapping("/eqpts")
    public List<Eqpt> getAllEqpts() {
        log.debug("REST request to get all Eqpts");
        return eqptRepository.findAll();
    }

    /**
     * {@code GET  /eqpts/:id} : get the "id" eqpt.
     *
     * @param id the id of the eqpt to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eqpt, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/eqpts/{id}")
    public ResponseEntity<Eqpt> getEqpt(@PathVariable Long id) {
        log.debug("REST request to get Eqpt : {}", id);
        Optional<Eqpt> eqpt = eqptRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(eqpt);
    }

    /**
     * {@code DELETE  /eqpts/:id} : delete the "id" eqpt.
     *
     * @param id the id of the eqpt to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/eqpts/{id}")
    public ResponseEntity<Void> deleteEqpt(@PathVariable Long id) {
        log.debug("REST request to delete Eqpt : {}", id);
        eqptRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
