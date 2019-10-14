package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestmdcApp;
import com.mycompany.myapp.domain.MaintenanceConfCombination;
import com.mycompany.myapp.repository.MaintenanceConfCombinationRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MaintenanceConfCombinationResource} REST controller.
 */
@SpringBootTest(classes = TestmdcApp.class)
public class MaintenanceConfCombinationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private MaintenanceConfCombinationRepository maintenanceConfCombinationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restMaintenanceConfCombinationMockMvc;

    private MaintenanceConfCombination maintenanceConfCombination;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MaintenanceConfCombinationResource maintenanceConfCombinationResource = new MaintenanceConfCombinationResource(maintenanceConfCombinationRepository);
        this.restMaintenanceConfCombinationMockMvc = MockMvcBuilders.standaloneSetup(maintenanceConfCombinationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaintenanceConfCombination createEntity(EntityManager em) {
        MaintenanceConfCombination maintenanceConfCombination = new MaintenanceConfCombination()
            .name(DEFAULT_NAME);
        return maintenanceConfCombination;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaintenanceConfCombination createUpdatedEntity(EntityManager em) {
        MaintenanceConfCombination maintenanceConfCombination = new MaintenanceConfCombination()
            .name(UPDATED_NAME);
        return maintenanceConfCombination;
    }

    @BeforeEach
    public void initTest() {
        maintenanceConfCombination = createEntity(em);
    }

    @Test
    @Transactional
    public void createMaintenanceConfCombination() throws Exception {
        int databaseSizeBeforeCreate = maintenanceConfCombinationRepository.findAll().size();

        // Create the MaintenanceConfCombination
        restMaintenanceConfCombinationMockMvc.perform(post("/api/maintenance-conf-combinations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(maintenanceConfCombination)))
            .andExpect(status().isCreated());

        // Validate the MaintenanceConfCombination in the database
        List<MaintenanceConfCombination> maintenanceConfCombinationList = maintenanceConfCombinationRepository.findAll();
        assertThat(maintenanceConfCombinationList).hasSize(databaseSizeBeforeCreate + 1);
        MaintenanceConfCombination testMaintenanceConfCombination = maintenanceConfCombinationList.get(maintenanceConfCombinationList.size() - 1);
        assertThat(testMaintenanceConfCombination.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createMaintenanceConfCombinationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = maintenanceConfCombinationRepository.findAll().size();

        // Create the MaintenanceConfCombination with an existing ID
        maintenanceConfCombination.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaintenanceConfCombinationMockMvc.perform(post("/api/maintenance-conf-combinations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(maintenanceConfCombination)))
            .andExpect(status().isBadRequest());

        // Validate the MaintenanceConfCombination in the database
        List<MaintenanceConfCombination> maintenanceConfCombinationList = maintenanceConfCombinationRepository.findAll();
        assertThat(maintenanceConfCombinationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = maintenanceConfCombinationRepository.findAll().size();
        // set the field null
        maintenanceConfCombination.setName(null);

        // Create the MaintenanceConfCombination, which fails.

        restMaintenanceConfCombinationMockMvc.perform(post("/api/maintenance-conf-combinations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(maintenanceConfCombination)))
            .andExpect(status().isBadRequest());

        List<MaintenanceConfCombination> maintenanceConfCombinationList = maintenanceConfCombinationRepository.findAll();
        assertThat(maintenanceConfCombinationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMaintenanceConfCombinations() throws Exception {
        // Initialize the database
        maintenanceConfCombinationRepository.saveAndFlush(maintenanceConfCombination);

        // Get all the maintenanceConfCombinationList
        restMaintenanceConfCombinationMockMvc.perform(get("/api/maintenance-conf-combinations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(maintenanceConfCombination.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getMaintenanceConfCombination() throws Exception {
        // Initialize the database
        maintenanceConfCombinationRepository.saveAndFlush(maintenanceConfCombination);

        // Get the maintenanceConfCombination
        restMaintenanceConfCombinationMockMvc.perform(get("/api/maintenance-conf-combinations/{id}", maintenanceConfCombination.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(maintenanceConfCombination.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingMaintenanceConfCombination() throws Exception {
        // Get the maintenanceConfCombination
        restMaintenanceConfCombinationMockMvc.perform(get("/api/maintenance-conf-combinations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaintenanceConfCombination() throws Exception {
        // Initialize the database
        maintenanceConfCombinationRepository.saveAndFlush(maintenanceConfCombination);

        int databaseSizeBeforeUpdate = maintenanceConfCombinationRepository.findAll().size();

        // Update the maintenanceConfCombination
        MaintenanceConfCombination updatedMaintenanceConfCombination = maintenanceConfCombinationRepository.findById(maintenanceConfCombination.getId()).get();
        // Disconnect from session so that the updates on updatedMaintenanceConfCombination are not directly saved in db
        em.detach(updatedMaintenanceConfCombination);
        updatedMaintenanceConfCombination
            .name(UPDATED_NAME);

        restMaintenanceConfCombinationMockMvc.perform(put("/api/maintenance-conf-combinations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMaintenanceConfCombination)))
            .andExpect(status().isOk());

        // Validate the MaintenanceConfCombination in the database
        List<MaintenanceConfCombination> maintenanceConfCombinationList = maintenanceConfCombinationRepository.findAll();
        assertThat(maintenanceConfCombinationList).hasSize(databaseSizeBeforeUpdate);
        MaintenanceConfCombination testMaintenanceConfCombination = maintenanceConfCombinationList.get(maintenanceConfCombinationList.size() - 1);
        assertThat(testMaintenanceConfCombination.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMaintenanceConfCombination() throws Exception {
        int databaseSizeBeforeUpdate = maintenanceConfCombinationRepository.findAll().size();

        // Create the MaintenanceConfCombination

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaintenanceConfCombinationMockMvc.perform(put("/api/maintenance-conf-combinations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(maintenanceConfCombination)))
            .andExpect(status().isBadRequest());

        // Validate the MaintenanceConfCombination in the database
        List<MaintenanceConfCombination> maintenanceConfCombinationList = maintenanceConfCombinationRepository.findAll();
        assertThat(maintenanceConfCombinationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMaintenanceConfCombination() throws Exception {
        // Initialize the database
        maintenanceConfCombinationRepository.saveAndFlush(maintenanceConfCombination);

        int databaseSizeBeforeDelete = maintenanceConfCombinationRepository.findAll().size();

        // Delete the maintenanceConfCombination
        restMaintenanceConfCombinationMockMvc.perform(delete("/api/maintenance-conf-combinations/{id}", maintenanceConfCombination.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MaintenanceConfCombination> maintenanceConfCombinationList = maintenanceConfCombinationRepository.findAll();
        assertThat(maintenanceConfCombinationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaintenanceConfCombination.class);
        MaintenanceConfCombination maintenanceConfCombination1 = new MaintenanceConfCombination();
        maintenanceConfCombination1.setId(1L);
        MaintenanceConfCombination maintenanceConfCombination2 = new MaintenanceConfCombination();
        maintenanceConfCombination2.setId(maintenanceConfCombination1.getId());
        assertThat(maintenanceConfCombination1).isEqualTo(maintenanceConfCombination2);
        maintenanceConfCombination2.setId(2L);
        assertThat(maintenanceConfCombination1).isNotEqualTo(maintenanceConfCombination2);
        maintenanceConfCombination1.setId(null);
        assertThat(maintenanceConfCombination1).isNotEqualTo(maintenanceConfCombination2);
    }
}
