package com.cangou.gwd.web.rest;

import com.cangou.gwd.domain.Freelancer;
import com.cangou.gwd.service.FreelancerService;
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
 * REST controller for managing {@link com.cangou.gwd.domain.Freelancer}.
 */
@RestController
@RequestMapping("/api")
public class FreelancerResource {

    private final Logger log = LoggerFactory.getLogger(FreelancerResource.class);

    private static final String ENTITY_NAME = "freelancer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FreelancerService freelancerService;

    public FreelancerResource(FreelancerService freelancerService) {
        this.freelancerService = freelancerService;
    }

    /**
     * {@code POST  /freelancers} : Create a new freelancer.
     *
     * @param freelancer the freelancer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new freelancer, or with status {@code 400 (Bad Request)} if the freelancer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/freelancers")
    public ResponseEntity<Freelancer> createFreelancer(@RequestBody Freelancer freelancer) throws URISyntaxException {
        log.debug("REST request to save Freelancer : {}", freelancer);
        if (freelancer.getId() != null) {
            throw new BadRequestAlertException("A new freelancer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Freelancer result = freelancerService.save(freelancer);
        return ResponseEntity.created(new URI("/api/freelancers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /freelancers} : Updates an existing freelancer.
     *
     * @param freelancer the freelancer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated freelancer,
     * or with status {@code 400 (Bad Request)} if the freelancer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the freelancer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/freelancers")
    public ResponseEntity<Freelancer> updateFreelancer(@RequestBody Freelancer freelancer) throws URISyntaxException {
        log.debug("REST request to update Freelancer : {}", freelancer);
        if (freelancer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Freelancer result = freelancerService.save(freelancer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, freelancer.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /freelancers} : get all the freelancers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of freelancers in body.
     */
    @GetMapping("/freelancers")
    public List<Freelancer> getAllFreelancers() {
        log.debug("REST request to get all Freelancers");
        return freelancerService.findAll();
    }

    /**
     * {@code GET  /freelancers/:id} : get the "id" freelancer.
     *
     * @param id the id of the freelancer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the freelancer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/freelancers/{id}")
    public ResponseEntity<Freelancer> getFreelancer(@PathVariable Long id) {
        log.debug("REST request to get Freelancer : {}", id);
        Optional<Freelancer> freelancer = freelancerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(freelancer);
    }

    /**
     * {@code DELETE  /freelancers/:id} : delete the "id" freelancer.
     *
     * @param id the id of the freelancer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/freelancers/{id}")
    public ResponseEntity<Void> deleteFreelancer(@PathVariable Long id) {
        log.debug("REST request to delete Freelancer : {}", id);
        freelancerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
