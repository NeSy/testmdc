package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.AircraftConf;
import com.mycompany.myapp.repository.AircraftConfRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.AircraftConf}.
 */
@RestController
@RequestMapping("/api")
public class AircraftConfResource {

    private final Logger log = LoggerFactory.getLogger(AircraftConfResource.class);

    private static final String ENTITY_NAME = "aircraftConf";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AircraftConfRepository aircraftConfRepository;

    public AircraftConfResource(AircraftConfRepository aircraftConfRepository) {
        this.aircraftConfRepository = aircraftConfRepository;
    }

    /**
     * {@code POST  /aircraft-confs} : Create a new aircraftConf.
     *
     * @param aircraftConf the aircraftConf to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aircraftConf, or with status {@code 400 (Bad Request)} if the aircraftConf has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aircraft-confs")
    public ResponseEntity<AircraftConf> createAircraftConf(@Valid @RequestBody AircraftConf aircraftConf) throws URISyntaxException {
        log.debug("REST request to save AircraftConf : {}", aircraftConf);
        if (aircraftConf.getId() != null) {
            throw new BadRequestAlertException("A new aircraftConf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AircraftConf result = aircraftConfRepository.save(aircraftConf);
        return ResponseEntity.created(new URI("/api/aircraft-confs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aircraft-confs} : Updates an existing aircraftConf.
     *
     * @param aircraftConf the aircraftConf to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aircraftConf,
     * or with status {@code 400 (Bad Request)} if the aircraftConf is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aircraftConf couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aircraft-confs")
    public ResponseEntity<AircraftConf> updateAircraftConf(@Valid @RequestBody AircraftConf aircraftConf) throws URISyntaxException {
        log.debug("REST request to update AircraftConf : {}", aircraftConf);
        if (aircraftConf.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AircraftConf result = aircraftConfRepository.save(aircraftConf);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aircraftConf.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /aircraft-confs} : get all the aircraftConfs.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aircraftConfs in body.
     */
    @GetMapping("/aircraft-confs")
    public List<AircraftConf> getAllAircraftConfs() {
        log.debug("REST request to get all AircraftConfs");
        return aircraftConfRepository.findAll();
    }

    /**
     * {@code GET  /aircraft-confs/:id} : get the "id" aircraftConf.
     *
     * @param id the id of the aircraftConf to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aircraftConf, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aircraft-confs/{id}")
    public ResponseEntity<AircraftConf> getAircraftConf(@PathVariable Long id) {
        log.debug("REST request to get AircraftConf : {}", id);
        Optional<AircraftConf> aircraftConf = aircraftConfRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aircraftConf);
    }

    /**
     * {@code DELETE  /aircraft-confs/:id} : delete the "id" aircraftConf.
     *
     * @param id the id of the aircraftConf to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aircraft-confs/{id}")
    public ResponseEntity<Void> deleteAircraftConf(@PathVariable Long id) {
        log.debug("REST request to delete AircraftConf : {}", id);
        aircraftConfRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
