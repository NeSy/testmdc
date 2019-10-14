package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Td;
import com.mycompany.myapp.repository.TdRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Td}.
 */
@RestController
@RequestMapping("/api")
public class TdResource {

    private final Logger log = LoggerFactory.getLogger(TdResource.class);

    private static final String ENTITY_NAME = "td";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TdRepository tdRepository;

    public TdResource(TdRepository tdRepository) {
        this.tdRepository = tdRepository;
    }

    /**
     * {@code POST  /tds} : Create a new td.
     *
     * @param td the td to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new td, or with status {@code 400 (Bad Request)} if the td has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tds")
    public ResponseEntity<Td> createTd(@Valid @RequestBody Td td) throws URISyntaxException {
        log.debug("REST request to save Td : {}", td);
        if (td.getId() != null) {
            throw new BadRequestAlertException("A new td cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Td result = tdRepository.save(td);
        return ResponseEntity.created(new URI("/api/tds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tds} : Updates an existing td.
     *
     * @param td the td to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated td,
     * or with status {@code 400 (Bad Request)} if the td is not valid,
     * or with status {@code 500 (Internal Server Error)} if the td couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tds")
    public ResponseEntity<Td> updateTd(@Valid @RequestBody Td td) throws URISyntaxException {
        log.debug("REST request to update Td : {}", td);
        if (td.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Td result = tdRepository.save(td);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, td.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tds} : get all the tds.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tds in body.
     */
    @GetMapping("/tds")
    public List<Td> getAllTds() {
        log.debug("REST request to get all Tds");
        return tdRepository.findAll();
    }

    /**
     * {@code GET  /tds/:id} : get the "id" td.
     *
     * @param id the id of the td to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the td, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tds/{id}")
    public ResponseEntity<Td> getTd(@PathVariable Long id) {
        log.debug("REST request to get Td : {}", id);
        Optional<Td> td = tdRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(td);
    }

    /**
     * {@code DELETE  /tds/:id} : delete the "id" td.
     *
     * @param id the id of the td to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tds/{id}")
    public ResponseEntity<Void> deleteTd(@PathVariable Long id) {
        log.debug("REST request to delete Td : {}", id);
        tdRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
