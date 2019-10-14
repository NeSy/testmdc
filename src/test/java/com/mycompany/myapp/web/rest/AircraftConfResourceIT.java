package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestmdcApp;
import com.mycompany.myapp.domain.AircraftConf;
import com.mycompany.myapp.repository.AircraftConfRepository;
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
 * Integration tests for the {@link AircraftConfResource} REST controller.
 */
@SpringBootTest(classes = TestmdcApp.class)
public class AircraftConfResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private AircraftConfRepository aircraftConfRepository;

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

    private MockMvc restAircraftConfMockMvc;

    private AircraftConf aircraftConf;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AircraftConfResource aircraftConfResource = new AircraftConfResource(aircraftConfRepository);
        this.restAircraftConfMockMvc = MockMvcBuilders.standaloneSetup(aircraftConfResource)
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
    public static AircraftConf createEntity(EntityManager em) {
        AircraftConf aircraftConf = new AircraftConf()
            .name(DEFAULT_NAME);
        return aircraftConf;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AircraftConf createUpdatedEntity(EntityManager em) {
        AircraftConf aircraftConf = new AircraftConf()
            .name(UPDATED_NAME);
        return aircraftConf;
    }

    @BeforeEach
    public void initTest() {
        aircraftConf = createEntity(em);
    }

    @Test
    @Transactional
    public void createAircraftConf() throws Exception {
        int databaseSizeBeforeCreate = aircraftConfRepository.findAll().size();

        // Create the AircraftConf
        restAircraftConfMockMvc.perform(post("/api/aircraft-confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aircraftConf)))
            .andExpect(status().isCreated());

        // Validate the AircraftConf in the database
        List<AircraftConf> aircraftConfList = aircraftConfRepository.findAll();
        assertThat(aircraftConfList).hasSize(databaseSizeBeforeCreate + 1);
        AircraftConf testAircraftConf = aircraftConfList.get(aircraftConfList.size() - 1);
        assertThat(testAircraftConf.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createAircraftConfWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aircraftConfRepository.findAll().size();

        // Create the AircraftConf with an existing ID
        aircraftConf.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAircraftConfMockMvc.perform(post("/api/aircraft-confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aircraftConf)))
            .andExpect(status().isBadRequest());

        // Validate the AircraftConf in the database
        List<AircraftConf> aircraftConfList = aircraftConfRepository.findAll();
        assertThat(aircraftConfList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = aircraftConfRepository.findAll().size();
        // set the field null
        aircraftConf.setName(null);

        // Create the AircraftConf, which fails.

        restAircraftConfMockMvc.perform(post("/api/aircraft-confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aircraftConf)))
            .andExpect(status().isBadRequest());

        List<AircraftConf> aircraftConfList = aircraftConfRepository.findAll();
        assertThat(aircraftConfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAircraftConfs() throws Exception {
        // Initialize the database
        aircraftConfRepository.saveAndFlush(aircraftConf);

        // Get all the aircraftConfList
        restAircraftConfMockMvc.perform(get("/api/aircraft-confs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aircraftConf.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getAircraftConf() throws Exception {
        // Initialize the database
        aircraftConfRepository.saveAndFlush(aircraftConf);

        // Get the aircraftConf
        restAircraftConfMockMvc.perform(get("/api/aircraft-confs/{id}", aircraftConf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(aircraftConf.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingAircraftConf() throws Exception {
        // Get the aircraftConf
        restAircraftConfMockMvc.perform(get("/api/aircraft-confs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAircraftConf() throws Exception {
        // Initialize the database
        aircraftConfRepository.saveAndFlush(aircraftConf);

        int databaseSizeBeforeUpdate = aircraftConfRepository.findAll().size();

        // Update the aircraftConf
        AircraftConf updatedAircraftConf = aircraftConfRepository.findById(aircraftConf.getId()).get();
        // Disconnect from session so that the updates on updatedAircraftConf are not directly saved in db
        em.detach(updatedAircraftConf);
        updatedAircraftConf
            .name(UPDATED_NAME);

        restAircraftConfMockMvc.perform(put("/api/aircraft-confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAircraftConf)))
            .andExpect(status().isOk());

        // Validate the AircraftConf in the database
        List<AircraftConf> aircraftConfList = aircraftConfRepository.findAll();
        assertThat(aircraftConfList).hasSize(databaseSizeBeforeUpdate);
        AircraftConf testAircraftConf = aircraftConfList.get(aircraftConfList.size() - 1);
        assertThat(testAircraftConf.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingAircraftConf() throws Exception {
        int databaseSizeBeforeUpdate = aircraftConfRepository.findAll().size();

        // Create the AircraftConf

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAircraftConfMockMvc.perform(put("/api/aircraft-confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aircraftConf)))
            .andExpect(status().isBadRequest());

        // Validate the AircraftConf in the database
        List<AircraftConf> aircraftConfList = aircraftConfRepository.findAll();
        assertThat(aircraftConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAircraftConf() throws Exception {
        // Initialize the database
        aircraftConfRepository.saveAndFlush(aircraftConf);

        int databaseSizeBeforeDelete = aircraftConfRepository.findAll().size();

        // Delete the aircraftConf
        restAircraftConfMockMvc.perform(delete("/api/aircraft-confs/{id}", aircraftConf.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AircraftConf> aircraftConfList = aircraftConfRepository.findAll();
        assertThat(aircraftConfList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AircraftConf.class);
        AircraftConf aircraftConf1 = new AircraftConf();
        aircraftConf1.setId(1L);
        AircraftConf aircraftConf2 = new AircraftConf();
        aircraftConf2.setId(aircraftConf1.getId());
        assertThat(aircraftConf1).isEqualTo(aircraftConf2);
        aircraftConf2.setId(2L);
        assertThat(aircraftConf1).isNotEqualTo(aircraftConf2);
        aircraftConf1.setId(null);
        assertThat(aircraftConf1).isNotEqualTo(aircraftConf2);
    }
}
