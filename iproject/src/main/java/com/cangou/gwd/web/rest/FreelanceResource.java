package com.cangou.gwd.web.rest;

import com.cangou.gwd.domain.Freelance;
import com.cangou.gwd.service.FreelanceService;
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
 * REST controller for managing {@link com.cangou.gwd.domain.Freelance}.
 */
@RestController
@RequestMapping("/api")
public class FreelanceResource {

    private final Logger log = LoggerFactory.getLogger(FreelanceResource.class);

    private static final String ENTITY_NAME = "freelance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FreelanceService freelanceService;

    public FreelanceResource(FreelanceService freelanceService) {
        this.freelanceService = freelanceService;
    }

    /**
     * {@code POST  /freelances} : Create a new freelance.
     *
     * @param freelance the freelance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new freelance, or with status {@code 400 (Bad Request)} if the freelance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/freelances")
    public ResponseEntity<Freelance> createFreelance(@RequestBody Freelance freelance) throws URISyntaxException {
        log.debug("REST request to save Freelance : {}", freelance);
        if (freelance.getId() != null) {
            throw new BadRequestAlertException("A new freelance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Freelance result = freelanceService.save(freelance);
        return ResponseEntity.created(new URI("/api/freelances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /freelances} : Updates an existing freelance.
     *
     * @param freelance the freelance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated freelance,
     * or with status {@code 400 (Bad Request)} if the freelance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the freelance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/freelances")
    public ResponseEntity<Freelance> updateFreelance(@RequestBody Freelance freelance) throws URISyntaxException {
        log.debug("REST request to update Freelance : {}", freelance);
        if (freelance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Freelance result = freelanceService.save(freelance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, freelance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /freelances} : get all the freelances.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of freelances in body.
     */
    @GetMapping("/freelances")
    public List<Freelance> getAllFreelances() {
        log.debug("REST request to get all Freelances");
        return freelanceService.findAll();
    }

    /**
     * {@code GET  /freelances/:id} : get the "id" freelance.
     *
     * @param id the id of the freelance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the freelance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/freelances/{id}")
    public ResponseEntity<Freelance> getFreelance(@PathVariable Long id) {
        log.debug("REST request to get Freelance : {}", id);
        Optional<Freelance> freelance = freelanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(freelance);
    }

    /**
     * {@code DELETE  /freelances/:id} : delete the "id" freelance.
     *
     * @param id the id of the freelance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/freelances/{id}")
    public ResponseEntity<Void> deleteFreelance(@PathVariable Long id) {
        log.debug("REST request to delete Freelance : {}", id);
        freelanceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
