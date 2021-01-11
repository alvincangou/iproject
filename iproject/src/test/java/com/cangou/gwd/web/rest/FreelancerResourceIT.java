package com.cangou.gwd.web.rest;

import com.cangou.gwd.IprojectApp;
import com.cangou.gwd.domain.Freelancer;
import com.cangou.gwd.repository.FreelancerRepository;
import com.cangou.gwd.service.FreelancerService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FreelancerResource} REST controller.
 */
@SpringBootTest(classes = IprojectApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FreelancerResourceIT {

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private FreelancerRepository freelancerRepository;

    @Autowired
    private FreelancerService freelancerService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFreelancerMockMvc;

    private Freelancer freelancer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Freelancer createEntity(EntityManager em) {
        Freelancer freelancer = new Freelancer()
            .postalCode(DEFAULT_POSTAL_CODE)
            .creationDate(DEFAULT_CREATION_DATE);
        return freelancer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Freelancer createUpdatedEntity(EntityManager em) {
        Freelancer freelancer = new Freelancer()
            .postalCode(UPDATED_POSTAL_CODE)
            .creationDate(UPDATED_CREATION_DATE);
        return freelancer;
    }

    @BeforeEach
    public void initTest() {
        freelancer = createEntity(em);
    }

    @Test
    @Transactional
    public void createFreelancer() throws Exception {
        int databaseSizeBeforeCreate = freelancerRepository.findAll().size();
        // Create the Freelancer
        restFreelancerMockMvc.perform(post("/api/freelancers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(freelancer)))
            .andExpect(status().isCreated());

        // Validate the Freelancer in the database
        List<Freelancer> freelancerList = freelancerRepository.findAll();
        assertThat(freelancerList).hasSize(databaseSizeBeforeCreate + 1);
        Freelancer testFreelancer = freelancerList.get(freelancerList.size() - 1);
        assertThat(testFreelancer.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testFreelancer.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
    }

    @Test
    @Transactional
    public void createFreelancerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = freelancerRepository.findAll().size();

        // Create the Freelancer with an existing ID
        freelancer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFreelancerMockMvc.perform(post("/api/freelancers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(freelancer)))
            .andExpect(status().isBadRequest());

        // Validate the Freelancer in the database
        List<Freelancer> freelancerList = freelancerRepository.findAll();
        assertThat(freelancerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFreelancers() throws Exception {
        // Initialize the database
        freelancerRepository.saveAndFlush(freelancer);

        // Get all the freelancerList
        restFreelancerMockMvc.perform(get("/api/freelancers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(freelancer.getId().intValue())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getFreelancer() throws Exception {
        // Initialize the database
        freelancerRepository.saveAndFlush(freelancer);

        // Get the freelancer
        restFreelancerMockMvc.perform(get("/api/freelancers/{id}", freelancer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(freelancer.getId().intValue()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingFreelancer() throws Exception {
        // Get the freelancer
        restFreelancerMockMvc.perform(get("/api/freelancers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreelancer() throws Exception {
        // Initialize the database
        freelancerService.save(freelancer);

        int databaseSizeBeforeUpdate = freelancerRepository.findAll().size();

        // Update the freelancer
        Freelancer updatedFreelancer = freelancerRepository.findById(freelancer.getId()).get();
        // Disconnect from session so that the updates on updatedFreelancer are not directly saved in db
        em.detach(updatedFreelancer);
        updatedFreelancer
            .postalCode(UPDATED_POSTAL_CODE)
            .creationDate(UPDATED_CREATION_DATE);

        restFreelancerMockMvc.perform(put("/api/freelancers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFreelancer)))
            .andExpect(status().isOk());

        // Validate the Freelancer in the database
        List<Freelancer> freelancerList = freelancerRepository.findAll();
        assertThat(freelancerList).hasSize(databaseSizeBeforeUpdate);
        Freelancer testFreelancer = freelancerList.get(freelancerList.size() - 1);
        assertThat(testFreelancer.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testFreelancer.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingFreelancer() throws Exception {
        int databaseSizeBeforeUpdate = freelancerRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFreelancerMockMvc.perform(put("/api/freelancers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(freelancer)))
            .andExpect(status().isBadRequest());

        // Validate the Freelancer in the database
        List<Freelancer> freelancerList = freelancerRepository.findAll();
        assertThat(freelancerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFreelancer() throws Exception {
        // Initialize the database
        freelancerService.save(freelancer);

        int databaseSizeBeforeDelete = freelancerRepository.findAll().size();

        // Delete the freelancer
        restFreelancerMockMvc.perform(delete("/api/freelancers/{id}", freelancer.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Freelancer> freelancerList = freelancerRepository.findAll();
        assertThat(freelancerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
