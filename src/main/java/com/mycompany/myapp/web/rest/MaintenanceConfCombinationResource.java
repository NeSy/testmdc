package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.MaintenanceConfCombination;
import com.mycompany.myapp.repository.MaintenanceConfCombinationRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.MaintenanceConfCombination}.
 */
@RestController
@RequestMapping("/api")
public class MaintenanceConfCombinationResource {

    private final Logger log = LoggerFactory.getLogger(MaintenanceConfCombinationResource.class);

    private static final String ENTITY_NAME = "maintenanceConfCombination";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MaintenanceConfCombinationRepository maintenanceConfCombinationRepository;

    public MaintenanceConfCombinationResource(MaintenanceConfCombinationRepository maintenanceConfCombinationRepository) {
        this.maintenanceConfCombinationRepository = maintenanceConfCombinationRepository;
    }

    /**
     * {@code POST  /maintenance-conf-combinations} : Create a new maintenanceConfCombination.
     *
     * @param maintenanceConfCombination the maintenanceConfCombination to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new maintenanceConfCombination, or with status {@code 400 (Bad Request)} if the maintenanceConfCombination has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/maintenance-conf-combinations")
    public ResponseEntity<MaintenanceConfCombination> createMaintenanceConfCombination(@Valid @RequestBody MaintenanceConfCombination maintenanceConfCombination) throws URISyntaxException {
        log.debug("REST request to save MaintenanceConfCombination : {}", maintenanceConfCombination);
        if (maintenanceConfCombination.getId() != null) {
            throw new BadRequestAlertException("A new maintenanceConfCombination cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MaintenanceConfCombination result = maintenanceConfCombinationRepository.save(maintenanceConfCombination);
        return ResponseEntity.created(new URI("/api/maintenance-conf-combinations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /maintenance-conf-combinations} : Updates an existing maintenanceConfCombination.
     *
     * @param maintenanceConfCombination the maintenanceConfCombination to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated maintenanceConfCombination,
     * or with status {@code 400 (Bad Request)} if the maintenanceConfCombination is not valid,
     * or with status {@code 500 (Internal Server Error)} if the maintenanceConfCombination couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/maintenance-conf-combinations")
    public ResponseEntity<MaintenanceConfCombination> updateMaintenanceConfCombination(@Valid @RequestBody MaintenanceConfCombination maintenanceConfCombination) throws URISyntaxException {
        log.debug("REST request to update MaintenanceConfCombination : {}", maintenanceConfCombination);
        if (maintenanceConfCombination.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MaintenanceConfCombination result = maintenanceConfCombinationRepository.save(maintenanceConfCombination);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, maintenanceConfCombination.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /maintenance-conf-combinations} : get all the maintenanceConfCombinations.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of maintenanceConfCombinations in body.
     */
    @GetMapping("/maintenance-conf-combinations")
    public List<MaintenanceConfCombination> getAllMaintenanceConfCombinations() {
        log.debug("REST request to get all MaintenanceConfCombinations");
        return maintenanceConfCombinationRepository.findAll();
    }

    /**
     * {@code GET  /maintenance-conf-combinations/:id} : get the "id" maintenanceConfCombination.
     *
     * @param id the id of the maintenanceConfCombination to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the maintenanceConfCombination, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/maintenance-conf-combinations/{id}")
    public ResponseEntity<MaintenanceConfCombination> getMaintenanceConfCombination(@PathVariable Long id) {
        log.debug("REST request to get MaintenanceConfCombination : {}", id);
        Optional<MaintenanceConfCombination> maintenanceConfCombination = maintenanceConfCombinationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(maintenanceConfCombination);
    }

    /**
     * {@code DELETE  /maintenance-conf-combinations/:id} : delete the "id" maintenanceConfCombination.
     *
     * @param id the id of the maintenanceConfCombination to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/maintenance-conf-combinations/{id}")
    public ResponseEntity<Void> deleteMaintenanceConfCombination(@PathVariable Long id) {
        log.debug("REST request to delete MaintenanceConfCombination : {}", id);
        maintenanceConfCombinationRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
