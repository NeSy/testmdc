package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestmdcApp;
import com.mycompany.myapp.domain.DicoCDM;
import com.mycompany.myapp.repository.DicoCDMRepository;
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
 * Integration tests for the {@link DicoCDMResource} REST controller.
 */
@SpringBootTest(classes = TestmdcApp.class)
public class DicoCDMResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PREV_DICO_ID = "AAAAAAAAAA";
    private static final String UPDATED_PREV_DICO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_IMT_REF = "AAAAAAAAAA";
    private static final String UPDATED_IMT_REF = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_RELEASE_DATE = "AAAAAAAAAA";
    private static final String UPDATED_RELEASE_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_TOOL_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_TOOL_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    @Autowired
    private DicoCDMRepository dicoCDMRepository;

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

    private MockMvc restDicoCDMMockMvc;

    private DicoCDM dicoCDM;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DicoCDMResource dicoCDMResource = new DicoCDMResource(dicoCDMRepository);
        this.restDicoCDMMockMvc = MockMvcBuilders.standaloneSetup(dicoCDMResource)
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
    public static DicoCDM createEntity(EntityManager em) {
        DicoCDM dicoCDM = new DicoCDM()
            .name(DEFAULT_NAME)
            .prevDicoId(DEFAULT_PREV_DICO_ID)
            .imtRef(DEFAULT_IMT_REF)
            .version(DEFAULT_VERSION)
            .date(DEFAULT_DATE)
            .releaseDate(DEFAULT_RELEASE_DATE)
            .toolVersion(DEFAULT_TOOL_VERSION)
            .state(DEFAULT_STATE);
        return dicoCDM;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DicoCDM createUpdatedEntity(EntityManager em) {
        DicoCDM dicoCDM = new DicoCDM()
            .name(UPDATED_NAME)
            .prevDicoId(UPDATED_PREV_DICO_ID)
            .imtRef(UPDATED_IMT_REF)
            .version(UPDATED_VERSION)
            .date(UPDATED_DATE)
            .releaseDate(UPDATED_RELEASE_DATE)
            .toolVersion(UPDATED_TOOL_VERSION)
            .state(UPDATED_STATE);
        return dicoCDM;
    }

    @BeforeEach
    public void initTest() {
        dicoCDM = createEntity(em);
    }

    @Test
    @Transactional
    public void createDicoCDM() throws Exception {
        int databaseSizeBeforeCreate = dicoCDMRepository.findAll().size();

        // Create the DicoCDM
        restDicoCDMMockMvc.perform(post("/api/dico-cdms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dicoCDM)))
            .andExpect(status().isCreated());

        // Validate the DicoCDM in the database
        List<DicoCDM> dicoCDMList = dicoCDMRepository.findAll();
        assertThat(dicoCDMList).hasSize(databaseSizeBeforeCreate + 1);
        DicoCDM testDicoCDM = dicoCDMList.get(dicoCDMList.size() - 1);
        assertThat(testDicoCDM.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDicoCDM.getPrevDicoId()).isEqualTo(DEFAULT_PREV_DICO_ID);
        assertThat(testDicoCDM.getImtRef()).isEqualTo(DEFAULT_IMT_REF);
        assertThat(testDicoCDM.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testDicoCDM.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDicoCDM.getReleaseDate()).isEqualTo(DEFAULT_RELEASE_DATE);
        assertThat(testDicoCDM.getToolVersion()).isEqualTo(DEFAULT_TOOL_VERSION);
        assertThat(testDicoCDM.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void createDicoCDMWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dicoCDMRepository.findAll().size();

        // Create the DicoCDM with an existing ID
        dicoCDM.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDicoCDMMockMvc.perform(post("/api/dico-cdms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dicoCDM)))
            .andExpect(status().isBadRequest());

        // Validate the DicoCDM in the database
        List<DicoCDM> dicoCDMList = dicoCDMRepository.findAll();
        assertThat(dicoCDMList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dicoCDMRepository.findAll().size();
        // set the field null
        dicoCDM.setName(null);

        // Create the DicoCDM, which fails.

        restDicoCDMMockMvc.perform(post("/api/dico-cdms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dicoCDM)))
            .andExpect(status().isBadRequest());

        List<DicoCDM> dicoCDMList = dicoCDMRepository.findAll();
        assertThat(dicoCDMList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = dicoCDMRepository.findAll().size();
        // set the field null
        dicoCDM.setVersion(null);

        // Create the DicoCDM, which fails.

        restDicoCDMMockMvc.perform(post("/api/dico-cdms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dicoCDM)))
            .andExpect(status().isBadRequest());

        List<DicoCDM> dicoCDMList = dicoCDMRepository.findAll();
        assertThat(dicoCDMList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = dicoCDMRepository.findAll().size();
        // set the field null
        dicoCDM.setDate(null);

        // Create the DicoCDM, which fails.

        restDicoCDMMockMvc.perform(post("/api/dico-cdms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dicoCDM)))
            .andExpect(status().isBadRequest());

        List<DicoCDM> dicoCDMList = dicoCDMRepository.findAll();
        assertThat(dicoCDMList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = dicoCDMRepository.findAll().size();
        // set the field null
        dicoCDM.setState(null);

        // Create the DicoCDM, which fails.

        restDicoCDMMockMvc.perform(post("/api/dico-cdms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dicoCDM)))
            .andExpect(status().isBadRequest());

        List<DicoCDM> dicoCDMList = dicoCDMRepository.findAll();
        assertThat(dicoCDMList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDicoCDMS() throws Exception {
        // Initialize the database
        dicoCDMRepository.saveAndFlush(dicoCDM);

        // Get all the dicoCDMList
        restDicoCDMMockMvc.perform(get("/api/dico-cdms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dicoCDM.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].prevDicoId").value(hasItem(DEFAULT_PREV_DICO_ID)))
            .andExpect(jsonPath("$.[*].imtRef").value(hasItem(DEFAULT_IMT_REF)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE)))
            .andExpect(jsonPath("$.[*].releaseDate").value(hasItem(DEFAULT_RELEASE_DATE)))
            .andExpect(jsonPath("$.[*].toolVersion").value(hasItem(DEFAULT_TOOL_VERSION)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)));
    }
    
    @Test
    @Transactional
    public void getDicoCDM() throws Exception {
        // Initialize the database
        dicoCDMRepository.saveAndFlush(dicoCDM);

        // Get the dicoCDM
        restDicoCDMMockMvc.perform(get("/api/dico-cdms/{id}", dicoCDM.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dicoCDM.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.prevDicoId").value(DEFAULT_PREV_DICO_ID))
            .andExpect(jsonPath("$.imtRef").value(DEFAULT_IMT_REF))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE))
            .andExpect(jsonPath("$.releaseDate").value(DEFAULT_RELEASE_DATE))
            .andExpect(jsonPath("$.toolVersion").value(DEFAULT_TOOL_VERSION))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE));
    }

    @Test
    @Transactional
    public void getNonExistingDicoCDM() throws Exception {
        // Get the dicoCDM
        restDicoCDMMockMvc.perform(get("/api/dico-cdms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDicoCDM() throws Exception {
        // Initialize the database
        dicoCDMRepository.saveAndFlush(dicoCDM);

        int databaseSizeBeforeUpdate = dicoCDMRepository.findAll().size();

        // Update the dicoCDM
        DicoCDM updatedDicoCDM = dicoCDMRepository.findById(dicoCDM.getId()).get();
        // Disconnect from session so that the updates on updatedDicoCDM are not directly saved in db
        em.detach(updatedDicoCDM);
        updatedDicoCDM
            .name(UPDATED_NAME)
            .prevDicoId(UPDATED_PREV_DICO_ID)
            .imtRef(UPDATED_IMT_REF)
            .version(UPDATED_VERSION)
            .date(UPDATED_DATE)
            .releaseDate(UPDATED_RELEASE_DATE)
            .toolVersion(UPDATED_TOOL_VERSION)
            .state(UPDATED_STATE);

        restDicoCDMMockMvc.perform(put("/api/dico-cdms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDicoCDM)))
            .andExpect(status().isOk());

        // Validate the DicoCDM in the database
        List<DicoCDM> dicoCDMList = dicoCDMRepository.findAll();
        assertThat(dicoCDMList).hasSize(databaseSizeBeforeUpdate);
        DicoCDM testDicoCDM = dicoCDMList.get(dicoCDMList.size() - 1);
        assertThat(testDicoCDM.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDicoCDM.getPrevDicoId()).isEqualTo(UPDATED_PREV_DICO_ID);
        assertThat(testDicoCDM.getImtRef()).isEqualTo(UPDATED_IMT_REF);
        assertThat(testDicoCDM.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testDicoCDM.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDicoCDM.getReleaseDate()).isEqualTo(UPDATED_RELEASE_DATE);
        assertThat(testDicoCDM.getToolVersion()).isEqualTo(UPDATED_TOOL_VERSION);
        assertThat(testDicoCDM.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDicoCDM() throws Exception {
        int databaseSizeBeforeUpdate = dicoCDMRepository.findAll().size();

        // Create the DicoCDM

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDicoCDMMockMvc.perform(put("/api/dico-cdms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dicoCDM)))
            .andExpect(status().isBadRequest());

        // Validate the DicoCDM in the database
        List<DicoCDM> dicoCDMList = dicoCDMRepository.findAll();
        assertThat(dicoCDMList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDicoCDM() throws Exception {
        // Initialize the database
        dicoCDMRepository.saveAndFlush(dicoCDM);

        int databaseSizeBeforeDelete = dicoCDMRepository.findAll().size();

        // Delete the dicoCDM
        restDicoCDMMockMvc.perform(delete("/api/dico-cdms/{id}", dicoCDM.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DicoCDM> dicoCDMList = dicoCDMRepository.findAll();
        assertThat(dicoCDMList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DicoCDM.class);
        DicoCDM dicoCDM1 = new DicoCDM();
        dicoCDM1.setId(1L);
        DicoCDM dicoCDM2 = new DicoCDM();
        dicoCDM2.setId(dicoCDM1.getId());
        assertThat(dicoCDM1).isEqualTo(dicoCDM2);
        dicoCDM2.setId(2L);
        assertThat(dicoCDM1).isNotEqualTo(dicoCDM2);
        dicoCDM1.setId(null);
        assertThat(dicoCDM1).isNotEqualTo(dicoCDM2);
    }
}
