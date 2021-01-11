package com.cangou.gwd.service;

import com.cangou.gwd.domain.Freelance;
import com.cangou.gwd.repository.FreelanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Freelance}.
 */
@Service
@Transactional
public class FreelanceService {

    private final Logger log = LoggerFactory.getLogger(FreelanceService.class);

    private final FreelanceRepository freelanceRepository;

    public FreelanceService(FreelanceRepository freelanceRepository) {
        this.freelanceRepository = freelanceRepository;
    }

    /**
     * Save a freelance.
     *
     * @param freelance the entity to save.
     * @return the persisted entity.
     */
    public Freelance save(Freelance freelance) {
        log.debug("Request to save Freelance : {}", freelance);
        return freelanceRepository.save(freelance);
    }

    /**
     * Get all the freelances.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Freelance> findAll() {
        log.debug("Request to get all Freelances");
        return freelanceRepository.findAll();
    }


    /**
     * Get one freelance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Freelance> findOne(Long id) {
        log.debug("Request to get Freelance : {}", id);
        return freelanceRepository.findById(id);
    }

    /**
     * Delete the freelance by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Freelance : {}", id);
        freelanceRepository.deleteById(id);
    }
}
