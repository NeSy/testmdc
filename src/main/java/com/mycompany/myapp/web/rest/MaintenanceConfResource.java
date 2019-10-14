package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.MaintenanceConf;
import com.mycompany.myapp.repository.MaintenanceConfRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.MaintenanceConf}.
 */
@RestController
@RequestMapping("/api")
public class MaintenanceConfResource {

    private final Logger log = LoggerFactory.getLogger(MaintenanceConfResource.class);

    private static final String ENTITY_NAME = "maintenanceConf";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MaintenanceConfRepository maintenanceConfRepository;

    public MaintenanceConfResource(MaintenanceConfRepository maintenanceConfRepository) {
        this.maintenanceConfRepository = maintenanceConfRepository;
    }

    /**
     * {@code POST  /maintenance-confs} : Create a new maintenanceConf.
     *
     * @param maintenanceConf the maintenanceConf to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new maintenanceConf, or with status {@code 400 (Bad Request)} if the maintenanceConf has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/maintenance-confs")
    public ResponseEntity<MaintenanceConf> createMaintenanceConf(@Valid @RequestBody MaintenanceConf maintenanceConf) throws URISyntaxException {
        log.debug("REST request to save MaintenanceConf : {}", maintenanceConf);
        if (maintenanceConf.getId() != null) {
            throw new BadRequestAlertException("A new maintenanceConf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MaintenanceConf result = maintenanceConfRepository.save(maintenanceConf);
        return ResponseEntity.created(new URI("/api/maintenance-confs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /maintenance-confs} : Updates an existing maintenanceConf.
     *
     * @param maintenanceConf the maintenanceConf to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated maintenanceConf,
     * or with status {@code 400 (Bad Request)} if the maintenanceConf is not valid,
     * or with status {@code 500 (Internal Server Error)} if the maintenanceConf couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/maintenance-confs")
    public ResponseEntity<MaintenanceConf> updateMaintenanceConf(@Valid @RequestBody MaintenanceConf maintenanceConf) throws URISyntaxException {
        log.debug("REST request to update MaintenanceConf : {}", maintenanceConf);
        if (maintenanceConf.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MaintenanceConf result = maintenanceConfRepository.save(maintenanceConf);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, maintenanceConf.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /maintenance-confs} : get all the maintenanceConfs.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of maintenanceConfs in body.
     */
    @GetMapping("/maintenance-confs")
    public List<MaintenanceConf> getAllMaintenanceConfs() {
        log.debug("REST request to get all MaintenanceConfs");
        return maintenanceConfRepository.findAll();
    }

    /**
     * {@code GET  /maintenance-confs/:id} : get the "id" maintenanceConf.
     *
     * @param id the id of the maintenanceConf to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the maintenanceConf, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/maintenance-confs/{id}")
    public ResponseEntity<MaintenanceConf> getMaintenanceConf(@PathVariable Long id) {
        log.debug("REST request to get MaintenanceConf : {}", id);
        Optional<MaintenanceConf> maintenanceConf = maintenanceConfRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(maintenanceConf);
    }

    /**
     * {@code DELETE  /maintenance-confs/:id} : delete the "id" maintenanceConf.
     *
     * @param id the id of the maintenanceConf to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/maintenance-confs/{id}")
    public ResponseEntity<Void> deleteMaintenanceConf(@PathVariable Long id) {
        log.debug("REST request to delete MaintenanceConf : {}", id);
        maintenanceConfRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
