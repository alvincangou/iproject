package com.cangou.gwd.service;

import com.cangou.gwd.domain.Work;
import com.cangou.gwd.repository.WorkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Work}.
 */
@Service
@Transactional
public class WorkService {

    private final Logger log = LoggerFactory.getLogger(WorkService.class);

    private final WorkRepository workRepository;

    public WorkService(WorkRepository workRepository) {
        this.workRepository = workRepository;
    }

    /**
     * Save a work.
     *
     * @param work the entity to save.
     * @return the persisted entity.
     */
    public Work save(Work work) {
        log.debug("Request to save Work : {}", work);
        return workRepository.save(work);
    }

    /**
     * Get all the works.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Work> findAll() {
        log.debug("Request to get all Works");
        return workRepository.findAllWithEagerRelationships();
    }


    /**
     * Get all the works with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Work> findAllWithEagerRelationships(Pageable pageable) {
        return workRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one work by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Work> findOne(Long id) {
        log.debug("Request to get Work : {}", id);
        return workRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the work by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Work : {}", id);
        workRepository.deleteById(id);
    }
}
