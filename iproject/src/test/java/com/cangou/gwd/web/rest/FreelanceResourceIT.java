package com.cangou.gwd.web.rest;

import com.cangou.gwd.IprojectApp;
import com.cangou.gwd.domain.Freelance;
import com.cangou.gwd.repository.FreelanceRepository;
import com.cangou.gwd.service.FreelanceService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FreelanceResource} REST controller.
 */
@SpringBootTest(classes = IprojectApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FreelanceResourceIT {

    @Autowired
    private FreelanceRepository freelanceRepository;

    @Autowired
    private FreelanceService freelanceService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFreelanceMockMvc;

    private Freelance freelance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Freelance createEntity(EntityManager em) {
        Freelance freelance = new Freelance();
        return freelance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Freelance createUpdatedEntity(EntityManager em) {
        Freelance freelance = new Freelance();
        return freelance;
    }

    @BeforeEach
    public void initTest() {
        freelance = createEntity(em);
    }

    @Test
    @Transactional
    public void createFreelance() throws Exception {
        int databaseSizeBeforeCreate = freelanceRepository.findAll().size();
        // Create the Freelance
        restFreelanceMockMvc.perform(post("/api/freelances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(freelance)))
            .andExpect(status().isCreated());

        // Validate the Freelance in the database
        List<Freelance> freelanceList = freelanceRepository.findAll();
        assertThat(freelanceList).hasSize(databaseSizeBeforeCreate + 1);
        Freelance testFreelance = freelanceList.get(freelanceList.size() - 1);
    }

    @Test
    @Transactional
    public void createFreelanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = freelanceRepository.findAll().size();

        // Create the Freelance with an existing ID
        freelance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFreelanceMockMvc.perform(post("/api/freelances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(freelance)))
            .andExpect(status().isBadRequest());

        // Validate the Freelance in the database
        List<Freelance> freelanceList = freelanceRepository.findAll();
        assertThat(freelanceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFreelances() throws Exception {
        // Initialize the database
        freelanceRepository.saveAndFlush(freelance);

        // Get all the freelanceList
        restFreelanceMockMvc.perform(get("/api/freelances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(freelance.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getFreelance() throws Exception {
        // Initialize the database
        freelanceRepository.saveAndFlush(freelance);

        // Get the freelance
        restFreelanceMockMvc.perform(get("/api/freelances/{id}", freelance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(freelance.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingFreelance() throws Exception {
        // Get the freelance
        restFreelanceMockMvc.perform(get("/api/freelances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreelance() throws Exception {
        // Initialize the database
        freelanceService.save(freelance);

        int databaseSizeBeforeUpdate = freelanceRepository.findAll().size();

        // Update the freelance
        Freelance updatedFreelance = freelanceRepository.findById(freelance.getId()).get();
        // Disconnect from session so that the updates on updatedFreelance are not directly saved in db
        em.detach(updatedFreelance);

        restFreelanceMockMvc.perform(put("/api/freelances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFreelance)))
            .andExpect(status().isOk());

        // Validate the Freelance in the database
        List<Freelance> freelanceList = freelanceRepository.findAll();
        assertThat(freelanceList).hasSize(databaseSizeBeforeUpdate);
        Freelance testFreelance = freelanceList.get(freelanceList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingFreelance() throws Exception {
        int databaseSizeBeforeUpdate = freelanceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFreelanceMockMvc.perform(put("/api/freelances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(freelance)))
            .andExpect(status().isBadRequest());

        // Validate the Freelance in the database
        List<Freelance> freelanceList = freelanceRepository.findAll();
        assertThat(freelanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFreelance() throws Exception {
        // Initialize the database
        freelanceService.save(freelance);

        int databaseSizeBeforeDelete = freelanceRepository.findAll().size();

        // Delete the freelance
        restFreelanceMockMvc.perform(delete("/api/freelances/{id}", freelance.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Freelance> freelanceList = freelanceRepository.findAll();
        assertThat(freelanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
