package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.DicoCDM;
import com.mycompany.myapp.repository.DicoCDMRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.DicoCDM}.
 */
@RestController
@RequestMapping("/api")
public class DicoCDMResource {

    private final Logger log = LoggerFactory.getLogger(DicoCDMResource.class);

    private static final String ENTITY_NAME = "dicoCDM";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DicoCDMRepository dicoCDMRepository;

    public DicoCDMResource(DicoCDMRepository dicoCDMRepository) {
        this.dicoCDMRepository = dicoCDMRepository;
    }

    /**
     * {@code POST  /dico-cdms} : Create a new dicoCDM.
     *
     * @param dicoCDM the dicoCDM to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dicoCDM, or with status {@code 400 (Bad Request)} if the dicoCDM has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dico-cdms")
    public ResponseEntity<DicoCDM> createDicoCDM(@Valid @RequestBody DicoCDM dicoCDM) throws URISyntaxException {
        log.debug("REST request to save DicoCDM : {}", dicoCDM);
        if (dicoCDM.getId() != null) {
            throw new BadRequestAlertException("A new dicoCDM cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DicoCDM result = dicoCDMRepository.save(dicoCDM);
        return ResponseEntity.created(new URI("/api/dico-cdms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dico-cdms} : Updates an existing dicoCDM.
     *
     * @param dicoCDM the dicoCDM to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dicoCDM,
     * or with status {@code 400 (Bad Request)} if the dicoCDM is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dicoCDM couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dico-cdms")
    public ResponseEntity<DicoCDM> updateDicoCDM(@Valid @RequestBody DicoCDM dicoCDM) throws URISyntaxException {
        log.debug("REST request to update DicoCDM : {}", dicoCDM);
        if (dicoCDM.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DicoCDM result = dicoCDMRepository.save(dicoCDM);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dicoCDM.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dico-cdms} : get all the dicoCDMS.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dicoCDMS in body.
     */
    @GetMapping("/dico-cdms")
    public List<DicoCDM> getAllDicoCDMS() {
        log.debug("REST request to get all DicoCDMS");
        return dicoCDMRepository.findAll();
    }

    /**
     * {@code GET  /dico-cdms/:id} : get the "id" dicoCDM.
     *
     * @param id the id of the dicoCDM to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dicoCDM, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dico-cdms/{id}")
    public ResponseEntity<DicoCDM> getDicoCDM(@PathVariable Long id) {
        log.debug("REST request to get DicoCDM : {}", id);
        Optional<DicoCDM> dicoCDM = dicoCDMRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dicoCDM);
    }

    /**
     * {@code DELETE  /dico-cdms/:id} : delete the "id" dicoCDM.
     *
     * @param id the id of the dicoCDM to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dico-cdms/{id}")
    public ResponseEntity<Void> deleteDicoCDM(@PathVariable Long id) {
        log.debug("REST request to delete DicoCDM : {}", id);
        dicoCDMRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
