package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PointEmport;
import com.mycompany.myapp.repository.PointEmportRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PointEmport}.
 */
@RestController
@RequestMapping("/api")
public class PointEmportResource {

    private final Logger log = LoggerFactory.getLogger(PointEmportResource.class);

    private static final String ENTITY_NAME = "pointEmport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PointEmportRepository pointEmportRepository;

    public PointEmportResource(PointEmportRepository pointEmportRepository) {
        this.pointEmportRepository = pointEmportRepository;
    }

    /**
     * {@code POST  /point-emports} : Create a new pointEmport.
     *
     * @param pointEmport the pointEmport to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pointEmport, or with status {@code 400 (Bad Request)} if the pointEmport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/point-emports")
    public ResponseEntity<PointEmport> createPointEmport(@Valid @RequestBody PointEmport pointEmport) throws URISyntaxException {
        log.debug("REST request to save PointEmport : {}", pointEmport);
        if (pointEmport.getId() != null) {
            throw new BadRequestAlertException("A new pointEmport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PointEmport result = pointEmportRepository.save(pointEmport);
        return ResponseEntity.created(new URI("/api/point-emports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /point-emports} : Updates an existing pointEmport.
     *
     * @param pointEmport the pointEmport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pointEmport,
     * or with status {@code 400 (Bad Request)} if the pointEmport is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pointEmport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/point-emports")
    public ResponseEntity<PointEmport> updatePointEmport(@Valid @RequestBody PointEmport pointEmport) throws URISyntaxException {
        log.debug("REST request to update PointEmport : {}", pointEmport);
        if (pointEmport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PointEmport result = pointEmportRepository.save(pointEmport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pointEmport.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /point-emports} : get all the pointEmports.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pointEmports in body.
     */
    @GetMapping("/point-emports")
    public List<PointEmport> getAllPointEmports() {
        log.debug("REST request to get all PointEmports");
        return pointEmportRepository.findAll();
    }

    /**
     * {@code GET  /point-emports/:id} : get the "id" pointEmport.
     *
     * @param id the id of the pointEmport to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pointEmport, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/point-emports/{id}")
    public ResponseEntity<PointEmport> getPointEmport(@PathVariable Long id) {
        log.debug("REST request to get PointEmport : {}", id);
        Optional<PointEmport> pointEmport = pointEmportRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pointEmport);
    }

    /**
     * {@code DELETE  /point-emports/:id} : delete the "id" pointEmport.
     *
     * @param id the id of the pointEmport to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/point-emports/{id}")
    public ResponseEntity<Void> deletePointEmport(@PathVariable Long id) {
        log.debug("REST request to delete PointEmport : {}", id);
        pointEmportRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
