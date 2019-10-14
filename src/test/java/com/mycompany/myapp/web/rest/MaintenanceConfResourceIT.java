package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestmdcApp;
import com.mycompany.myapp.domain.MaintenanceConf;
import com.mycompany.myapp.repository.MaintenanceConfRepository;
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
 * Integration tests for the {@link MaintenanceConfResource} REST controller.
 */
@SpringBootTest(classes = TestmdcApp.class)
public class MaintenanceConfResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private MaintenanceConfRepository maintenanceConfRepository;

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

    private MockMvc restMaintenanceConfMockMvc;

    private MaintenanceConf maintenanceConf;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MaintenanceConfResource maintenanceConfResource = new MaintenanceConfResource(maintenanceConfRepository);
        this.restMaintenanceConfMockMvc = MockMvcBuilders.standaloneSetup(maintenanceConfResource)
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
    public static MaintenanceConf createEntity(EntityManager em) {
        MaintenanceConf maintenanceConf = new MaintenanceConf()
            .name(DEFAULT_NAME);
        return maintenanceConf;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaintenanceConf createUpdatedEntity(EntityManager em) {
        MaintenanceConf maintenanceConf = new MaintenanceConf()
            .name(UPDATED_NAME);
        return maintenanceConf;
    }

    @BeforeEach
    public void initTest() {
        maintenanceConf = createEntity(em);
    }

    @Test
    @Transactional
    public void createMaintenanceConf() throws Exception {
        int databaseSizeBeforeCreate = maintenanceConfRepository.findAll().size();

        // Create the MaintenanceConf
        restMaintenanceConfMockMvc.perform(post("/api/maintenance-confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(maintenanceConf)))
            .andExpect(status().isCreated());

        // Validate the MaintenanceConf in the database
        List<MaintenanceConf> maintenanceConfList = maintenanceConfRepository.findAll();
        assertThat(maintenanceConfList).hasSize(databaseSizeBeforeCreate + 1);
        MaintenanceConf testMaintenanceConf = maintenanceConfList.get(maintenanceConfList.size() - 1);
        assertThat(testMaintenanceConf.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createMaintenanceConfWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = maintenanceConfRepository.findAll().size();

        // Create the MaintenanceConf with an existing ID
        maintenanceConf.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaintenanceConfMockMvc.perform(post("/api/maintenance-confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(maintenanceConf)))
            .andExpect(status().isBadRequest());

        // Validate the MaintenanceConf in the database
        List<MaintenanceConf> maintenanceConfList = maintenanceConfRepository.findAll();
        assertThat(maintenanceConfList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = maintenanceConfRepository.findAll().size();
        // set the field null
        maintenanceConf.setName(null);

        // Create the MaintenanceConf, which fails.

        restMaintenanceConfMockMvc.perform(post("/api/maintenance-confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(maintenanceConf)))
            .andExpect(status().isBadRequest());

        List<MaintenanceConf> maintenanceConfList = maintenanceConfRepository.findAll();
        assertThat(maintenanceConfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMaintenanceConfs() throws Exception {
        // Initialize the database
        maintenanceConfRepository.saveAndFlush(maintenanceConf);

        // Get all the maintenanceConfList
        restMaintenanceConfMockMvc.perform(get("/api/maintenance-confs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(maintenanceConf.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getMaintenanceConf() throws Exception {
        // Initialize the database
        maintenanceConfRepository.saveAndFlush(maintenanceConf);

        // Get the maintenanceConf
        restMaintenanceConfMockMvc.perform(get("/api/maintenance-confs/{id}", maintenanceConf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(maintenanceConf.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingMaintenanceConf() throws Exception {
        // Get the maintenanceConf
        restMaintenanceConfMockMvc.perform(get("/api/maintenance-confs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaintenanceConf() throws Exception {
        // Initialize the database
        maintenanceConfRepository.saveAndFlush(maintenanceConf);

        int databaseSizeBeforeUpdate = maintenanceConfRepository.findAll().size();

        // Update the maintenanceConf
        MaintenanceConf updatedMaintenanceConf = maintenanceConfRepository.findById(maintenanceConf.getId()).get();
        // Disconnect from session so that the updates on updatedMaintenanceConf are not directly saved in db
        em.detach(updatedMaintenanceConf);
        updatedMaintenanceConf
            .name(UPDATED_NAME);

        restMaintenanceConfMockMvc.perform(put("/api/maintenance-confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMaintenanceConf)))
            .andExpect(status().isOk());

        // Validate the MaintenanceConf in the database
        List<MaintenanceConf> maintenanceConfList = maintenanceConfRepository.findAll();
        assertThat(maintenanceConfList).hasSize(databaseSizeBeforeUpdate);
        MaintenanceConf testMaintenanceConf = maintenanceConfList.get(maintenanceConfList.size() - 1);
        assertThat(testMaintenanceConf.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMaintenanceConf() throws Exception {
        int databaseSizeBeforeUpdate = maintenanceConfRepository.findAll().size();

        // Create the MaintenanceConf

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaintenanceConfMockMvc.perform(put("/api/maintenance-confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(maintenanceConf)))
            .andExpect(status().isBadRequest());

        // Validate the MaintenanceConf in the database
        List<MaintenanceConf> maintenanceConfList = maintenanceConfRepository.findAll();
        assertThat(maintenanceConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMaintenanceConf() throws Exception {
        // Initialize the database
        maintenanceConfRepository.saveAndFlush(maintenanceConf);

        int databaseSizeBeforeDelete = maintenanceConfRepository.findAll().size();

        // Delete the maintenanceConf
        restMaintenanceConfMockMvc.perform(delete("/api/maintenance-confs/{id}", maintenanceConf.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MaintenanceConf> maintenanceConfList = maintenanceConfRepository.findAll();
        assertThat(maintenanceConfList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaintenanceConf.class);
        MaintenanceConf maintenanceConf1 = new MaintenanceConf();
        maintenanceConf1.setId(1L);
        MaintenanceConf maintenanceConf2 = new MaintenanceConf();
        maintenanceConf2.setId(maintenanceConf1.getId());
        assertThat(maintenanceConf1).isEqualTo(maintenanceConf2);
        maintenanceConf2.setId(2L);
        assertThat(maintenanceConf1).isNotEqualTo(maintenanceConf2);
        maintenanceConf1.setId(null);
        assertThat(maintenanceConf1).isNotEqualTo(maintenanceConf2);
    }
}
