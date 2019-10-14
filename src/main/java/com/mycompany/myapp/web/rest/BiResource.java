package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Bi;
import com.mycompany.myapp.repository.BiRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Bi}.
 */
@RestController
@RequestMapping("/api")
public class BiResource {

    private final Logger log = LoggerFactory.getLogger(BiResource.class);

    private static final String ENTITY_NAME = "bi";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BiRepository biRepository;

    public BiResource(BiRepository biRepository) {
        this.biRepository = biRepository;
    }

    /**
     * {@code POST  /bis} : Create a new bi.
     *
     * @param bi the bi to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bi, or with status {@code 400 (Bad Request)} if the bi has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bis")
    public ResponseEntity<Bi> createBi(@Valid @RequestBody Bi bi) throws URISyntaxException {
        log.debug("REST request to save Bi : {}", bi);
        if (bi.getId() != null) {
            throw new BadRequestAlertException("A new bi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bi result = biRepository.save(bi);
        return ResponseEntity.created(new URI("/api/bis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bis} : Updates an existing bi.
     *
     * @param bi the bi to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bi,
     * or with status {@code 400 (Bad Request)} if the bi is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bi couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bis")
    public ResponseEntity<Bi> updateBi(@Valid @RequestBody Bi bi) throws URISyntaxException {
        log.debug("REST request to update Bi : {}", bi);
        if (bi.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Bi result = biRepository.save(bi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bi.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bis} : get all the bis.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bis in body.
     */
    @GetMapping("/bis")
    public List<Bi> getAllBis() {
        log.debug("REST request to get all Bis");
        return biRepository.findAll();
    }

    /**
     * {@code GET  /bis/:id} : get the "id" bi.
     *
     * @param id the id of the bi to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bi, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bis/{id}")
    public ResponseEntity<Bi> getBi(@PathVariable Long id) {
        log.debug("REST request to get Bi : {}", id);
        Optional<Bi> bi = biRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bi);
    }

    /**
     * {@code DELETE  /bis/:id} : delete the "id" bi.
     *
     * @param id the id of the bi to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bis/{id}")
    public ResponseEntity<Void> deleteBi(@PathVariable Long id) {
        log.debug("REST request to delete Bi : {}", id);
        biRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
