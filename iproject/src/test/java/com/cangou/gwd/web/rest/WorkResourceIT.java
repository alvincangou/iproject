package com.cangou.gwd.web.rest;

import com.cangou.gwd.IprojectApp;
import com.cangou.gwd.domain.Work;
import com.cangou.gwd.repository.WorkRepository;
import com.cangou.gwd.service.WorkService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link WorkResource} REST controller.
 */
@SpringBootTest(classes = IprojectApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class WorkResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Duration DEFAULT_NECESSARY_TIME = Duration.ofHours(6);
    private static final Duration UPDATED_NECESSARY_TIME = Duration.ofHours(12);

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    @Autowired
    private WorkRepository workRepository;

    @Mock
    private WorkRepository workRepositoryMock;

    @Mock
    private WorkService workServiceMock;

    @Autowired
    private WorkService workService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkMockMvc;

    private Work work;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Work createEntity(EntityManager em) {
        Work work = new Work()
            .name(DEFAULT_NAME)
            .necessaryTime(DEFAULT_NECESSARY_TIME)
            .price(DEFAULT_PRICE);
        return work;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Work createUpdatedEntity(EntityManager em) {
        Work work = new Work()
            .name(UPDATED_NAME)
            .necessaryTime(UPDATED_NECESSARY_TIME)
            .price(UPDATED_PRICE);
        return work;
    }

    @BeforeEach
    public void initTest() {
        work = createEntity(em);
    }

    @Test
    @Transactional
    public void createWork() throws Exception {
        int databaseSizeBeforeCreate = workRepository.findAll().size();
        // Create the Work
        restWorkMockMvc.perform(post("/api/works")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(work)))
            .andExpect(status().isCreated());

        // Validate the Work in the database
        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeCreate + 1);
        Work testWork = workList.get(workList.size() - 1);
        assertThat(testWork.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWork.getNecessaryTime()).isEqualTo(DEFAULT_NECESSARY_TIME);
        assertThat(testWork.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createWorkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workRepository.findAll().size();

        // Create the Work with an existing ID
        work.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkMockMvc.perform(post("/api/works")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(work)))
            .andExpect(status().isBadRequest());

        // Validate the Work in the database
        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWorks() throws Exception {
        // Initialize the database
        workRepository.saveAndFlush(work);

        // Get all the workList
        restWorkMockMvc.perform(get("/api/works?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(work.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].necessaryTime").value(hasItem(DEFAULT_NECESSARY_TIME.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllWorksWithEagerRelationshipsIsEnabled() throws Exception {
        when(workServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restWorkMockMvc.perform(get("/api/works?eagerload=true"))
            .andExpect(status().isOk());

        verify(workServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllWorksWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(workServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restWorkMockMvc.perform(get("/api/works?eagerload=true"))
            .andExpect(status().isOk());

        verify(workServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getWork() throws Exception {
        // Initialize the database
        workRepository.saveAndFlush(work);

        // Get the work
        restWorkMockMvc.perform(get("/api/works/{id}", work.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(work.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.necessaryTime").value(DEFAULT_NECESSARY_TIME.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingWork() throws Exception {
        // Get the work
        restWorkMockMvc.perform(get("/api/works/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWork() throws Exception {
        // Initialize the database
        workService.save(work);

        int databaseSizeBeforeUpdate = workRepository.findAll().size();

        // Update the work
        Work updatedWork = workRepository.findById(work.getId()).get();
        // Disconnect from session so that the updates on updatedWork are not directly saved in db
        em.detach(updatedWork);
        updatedWork
            .name(UPDATED_NAME)
            .necessaryTime(UPDATED_NECESSARY_TIME)
            .price(UPDATED_PRICE);

        restWorkMockMvc.perform(put("/api/works")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWork)))
            .andExpect(status().isOk());

        // Validate the Work in the database
        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeUpdate);
        Work testWork = workList.get(workList.size() - 1);
        assertThat(testWork.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWork.getNecessaryTime()).isEqualTo(UPDATED_NECESSARY_TIME);
        assertThat(testWork.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingWork() throws Exception {
        int databaseSizeBeforeUpdate = workRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkMockMvc.perform(put("/api/works")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(work)))
            .andExpect(status().isBadRequest());

        // Validate the Work in the database
        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWork() throws Exception {
        // Initialize the database
        workService.save(work);

        int databaseSizeBeforeDelete = workRepository.findAll().size();

        // Delete the work
        restWorkMockMvc.perform(delete("/api/works/{id}", work.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
