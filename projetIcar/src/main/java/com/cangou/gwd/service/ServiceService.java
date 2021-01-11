package com.cangou.gwd.service;

import com.cangou.gwd.domain.Service;
import com.cangou.gwd.repository.ServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Service}.
 */
@Service
@Transactional
public class ServiceService {

    private final Logger log = LoggerFactory.getLogger(ServiceService.class);

    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    /**
     * Save a service.
     *
     * @param service the entity to save.
     * @return the persisted entity.
     */
    public Service save(Service service) {
        log.debug("Request to save Service : {}", service);
        return serviceRepository.save(service);
    }

    /**
     * Get all the services.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Service> findAll() {
        log.debug("Request to get all Services");
        return serviceRepository.findAll();
    }


    /**
     * Get one service by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Service> findOne(Long id) {
        log.debug("Request to get Service : {}", id);
        return serviceRepository.findById(id);
    }

    /**
     * Delete the service by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Service : {}", id);
        serviceRepository.deleteById(id);
    }
}
