package com.cangou.gwd.service;

import com.cangou.gwd.domain.Freelancer;
import com.cangou.gwd.repository.FreelancerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Freelancer}.
 */
@Service
@Transactional
public class FreelancerService {

    private final Logger log = LoggerFactory.getLogger(FreelancerService.class);

    private final FreelancerRepository freelancerRepository;

    public FreelancerService(FreelancerRepository freelancerRepository) {
        this.freelancerRepository = freelancerRepository;
    }

    /**
     * Save a freelancer.
     *
     * @param freelancer the entity to save.
     * @return the persisted entity.
     */
    public Freelancer save(Freelancer freelancer) {
        log.debug("Request to save Freelancer : {}", freelancer);
        return freelancerRepository.save(freelancer);
    }

    /**
     * Get all the freelancers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Freelancer> findAll() {
        log.debug("Request to get all Freelancers");
        return freelancerRepository.findAll();
    }


    /**
     * Get one freelancer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Freelancer> findOne(Long id) {
        log.debug("Request to get Freelancer : {}", id);
        return freelancerRepository.findById(id);
    }

    /**
     * Delete the freelancer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Freelancer : {}", id);
        freelancerRepository.deleteById(id);
    }
}
