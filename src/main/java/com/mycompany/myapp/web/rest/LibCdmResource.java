package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.LibCdm;
import com.mycompany.myapp.repository.LibCdmRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.LibCdm}.
 */
@RestController
@RequestMapping("/api")
public class LibCdmResource {

    private final Logger log = LoggerFactory.getLogger(LibCdmResource.class);

    private static final String ENTITY_NAME = "libCdm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LibCdmRepository libCdmRepository;

    public LibCdmResource(LibCdmRepository libCdmRepository) {
        this.libCdmRepository = libCdmRepository;
    }

    /**
     * {@code POST  /lib-cdms} : Create a new libCdm.
     *
     * @param libCdm the libCdm to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new libCdm, or with status {@code 400 (Bad Request)} if the libCdm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lib-cdms")
    public ResponseEntity<LibCdm> createLibCdm(@Valid @RequestBody LibCdm libCdm) throws URISyntaxException {
        log.debug("REST request to save LibCdm : {}", libCdm);
        if (libCdm.getId() != null) {
            throw new BadRequestAlertException("A new libCdm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LibCdm result = libCdmRepository.save(libCdm);
        return ResponseEntity.created(new URI("/api/lib-cdms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lib-cdms} : Updates an existing libCdm.
     *
     * @param libCdm the libCdm to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated libCdm,
     * or with status {@code 400 (Bad Request)} if the libCdm is not valid,
     * or with status {@code 500 (Internal Server Error)} if the libCdm couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lib-cdms")
    public ResponseEntity<LibCdm> updateLibCdm(@Valid @RequestBody LibCdm libCdm) throws URISyntaxException {
        log.debug("REST request to update LibCdm : {}", libCdm);
        if (libCdm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LibCdm result = libCdmRepository.save(libCdm);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, libCdm.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /lib-cdms} : get all the libCdms.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of libCdms in body.
     */
    @GetMapping("/lib-cdms")
    public List<LibCdm> getAllLibCdms() {
        log.debug("REST request to get all LibCdms");
        return libCdmRepository.findAll();
    }

    /**
     * {@code GET  /lib-cdms/:id} : get the "id" libCdm.
     *
     * @param id the id of the libCdm to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the libCdm, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lib-cdms/{id}")
    public ResponseEntity<LibCdm> getLibCdm(@PathVariable Long id) {
        log.debug("REST request to get LibCdm : {}", id);
        Optional<LibCdm> libCdm = libCdmRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(libCdm);
    }

    /**
     * {@code DELETE  /lib-cdms/:id} : delete the "id" libCdm.
     *
     * @param id the id of the libCdm to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lib-cdms/{id}")
    public ResponseEntity<Void> deleteLibCdm(@PathVariable Long id) {
        log.debug("REST request to delete LibCdm : {}", id);
        libCdmRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
