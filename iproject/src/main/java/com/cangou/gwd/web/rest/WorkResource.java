package com.cangou.gwd.web.rest;

import com.cangou.gwd.domain.Work;
import com.cangou.gwd.service.WorkService;
import com.cangou.gwd.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.cangou.gwd.domain.Work}.
 */
@RestController
@RequestMapping("/api")
public class WorkResource {

    private final Logger log = LoggerFactory.getLogger(WorkResource.class);

    private static final String ENTITY_NAME = "work";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkService workService;

    public WorkResource(WorkService workService) {
        this.workService = workService;
    }

    /**
     * {@code POST  /works} : Create a new work.
     *
     * @param work the work to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new work, or with status {@code 400 (Bad Request)} if the work has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/works")
    public ResponseEntity<Work> createWork(@RequestBody Work work) throws URISyntaxException {
        log.debug("REST request to save Work : {}", work);
        if (work.getId() != null) {
            throw new BadRequestAlertException("A new work cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Work result = workService.save(work);
        return ResponseEntity.created(new URI("/api/works/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /works} : Updates an existing work.
     *
     * @param work the work to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated work,
     * or with status {@code 400 (Bad Request)} if the work is not valid,
     * or with status {@code 500 (Internal Server Error)} if the work couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/works")
    public ResponseEntity<Work> updateWork(@RequestBody Work work) throws URISyntaxException {
        log.debug("REST request to update Work : {}", work);
        if (work.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Work result = workService.save(work);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, work.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /works} : get all the works.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of works in body.
     */
    @GetMapping("/works")
    public List<Work> getAllWorks(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Works");
        return workService.findAll();
    }

    /**
     * {@code GET  /works/:id} : get the "id" work.
     *
     * @param id the id of the work to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the work, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/works/{id}")
    public ResponseEntity<Work> getWork(@PathVariable Long id) {
        log.debug("REST request to get Work : {}", id);
        Optional<Work> work = workService.findOne(id);
        return ResponseUtil.wrapOrNotFound(work);
    }

    /**
     * {@code DELETE  /works/:id} : delete the "id" work.
     *
     * @param id the id of the work to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/works/{id}")
    public ResponseEntity<Void> deleteWork(@PathVariable Long id) {
        log.debug("REST request to delete Work : {}", id);
        workService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
