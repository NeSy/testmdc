package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestmdcApp;
import com.mycompany.myapp.domain.LibCdm;
import com.mycompany.myapp.repository.LibCdmRepository;
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
 * Integration tests for the {@link LibCdmResource} REST controller.
 */
@SpringBootTest(classes = TestmdcApp.class)
public class LibCdmResourceIT {

    private static final String DEFAULT_NAME_FR = "AAAAAAAAAA";
    private static final String UPDATED_NAME_FR = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_GB = "AAAAAAAAAA";
    private static final String UPDATED_NAME_GB = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT_FR = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT_FR = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT_GB = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT_GB = "BBBBBBBBBB";

    private static final String DEFAULT_DOC_NAME_FR = "AAAAAAAAAA";
    private static final String UPDATED_DOC_NAME_FR = "BBBBBBBBBB";

    private static final String DEFAULT_DOC_NAME_GB = "AAAAAAAAAA";
    private static final String UPDATED_DOC_NAME_GB = "BBBBBBBBBB";

    @Autowired
    private LibCdmRepository libCdmRepository;

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

    private MockMvc restLibCdmMockMvc;

    private LibCdm libCdm;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LibCdmResource libCdmResource = new LibCdmResource(libCdmRepository);
        this.restLibCdmMockMvc = MockMvcBuilders.standaloneSetup(libCdmResource)
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
    public static LibCdm createEntity(EntityManager em) {
        LibCdm libCdm = new LibCdm()
            .nameFR(DEFAULT_NAME_FR)
            .nameGB(DEFAULT_NAME_GB)
            .commentFR(DEFAULT_COMMENT_FR)
            .commentGB(DEFAULT_COMMENT_GB)
            .docNameFR(DEFAULT_DOC_NAME_FR)
            .docNameGB(DEFAULT_DOC_NAME_GB);
        return libCdm;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LibCdm createUpdatedEntity(EntityManager em) {
        LibCdm libCdm = new LibCdm()
            .nameFR(UPDATED_NAME_FR)
            .nameGB(UPDATED_NAME_GB)
            .commentFR(UPDATED_COMMENT_FR)
            .commentGB(UPDATED_COMMENT_GB)
            .docNameFR(UPDATED_DOC_NAME_FR)
            .docNameGB(UPDATED_DOC_NAME_GB);
        return libCdm;
    }

    @BeforeEach
    public void initTest() {
        libCdm = createEntity(em);
    }

    @Test
    @Transactional
    public void createLibCdm() throws Exception {
        int databaseSizeBeforeCreate = libCdmRepository.findAll().size();

        // Create the LibCdm
        restLibCdmMockMvc.perform(post("/api/lib-cdms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libCdm)))
            .andExpect(status().isCreated());

        // Validate the LibCdm in the database
        List<LibCdm> libCdmList = libCdmRepository.findAll();
        assertThat(libCdmList).hasSize(databaseSizeBeforeCreate + 1);
        LibCdm testLibCdm = libCdmList.get(libCdmList.size() - 1);
        assertThat(testLibCdm.getNameFR()).isEqualTo(DEFAULT_NAME_FR);
        assertThat(testLibCdm.getNameGB()).isEqualTo(DEFAULT_NAME_GB);
        assertThat(testLibCdm.getCommentFR()).isEqualTo(DEFAULT_COMMENT_FR);
        assertThat(testLibCdm.getCommentGB()).isEqualTo(DEFAULT_COMMENT_GB);
        assertThat(testLibCdm.getDocNameFR()).isEqualTo(DEFAULT_DOC_NAME_FR);
        assertThat(testLibCdm.getDocNameGB()).isEqualTo(DEFAULT_DOC_NAME_GB);
    }

    @Test
    @Transactional
    public void createLibCdmWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = libCdmRepository.findAll().size();

        // Create the LibCdm with an existing ID
        libCdm.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLibCdmMockMvc.perform(post("/api/lib-cdms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libCdm)))
            .andExpect(status().isBadRequest());

        // Validate the LibCdm in the database
        List<LibCdm> libCdmList = libCdmRepository.findAll();
        assertThat(libCdmList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameFRIsRequired() throws Exception {
        int databaseSizeBeforeTest = libCdmRepository.findAll().size();
        // set the field null
        libCdm.setNameFR(null);

        // Create the LibCdm, which fails.

        restLibCdmMockMvc.perform(post("/api/lib-cdms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libCdm)))
            .andExpect(status().isBadRequest());

        List<LibCdm> libCdmList = libCdmRepository.findAll();
        assertThat(libCdmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCommentFRIsRequired() throws Exception {
        int databaseSizeBeforeTest = libCdmRepository.findAll().size();
        // set the field null
        libCdm.setCommentFR(null);

        // Create the LibCdm, which fails.

        restLibCdmMockMvc.perform(post("/api/lib-cdms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libCdm)))
            .andExpect(status().isBadRequest());

        List<LibCdm> libCdmList = libCdmRepository.findAll();
        assertThat(libCdmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocNameFRIsRequired() throws Exception {
        int databaseSizeBeforeTest = libCdmRepository.findAll().size();
        // set the field null
        libCdm.setDocNameFR(null);

        // Create the LibCdm, which fails.

        restLibCdmMockMvc.perform(post("/api/lib-cdms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libCdm)))
            .andExpect(status().isBadRequest());

        List<LibCdm> libCdmList = libCdmRepository.findAll();
        assertThat(libCdmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLibCdms() throws Exception {
        // Initialize the database
        libCdmRepository.saveAndFlush(libCdm);

        // Get all the libCdmList
        restLibCdmMockMvc.perform(get("/api/lib-cdms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(libCdm.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameFR").value(hasItem(DEFAULT_NAME_FR)))
            .andExpect(jsonPath("$.[*].nameGB").value(hasItem(DEFAULT_NAME_GB)))
            .andExpect(jsonPath("$.[*].commentFR").value(hasItem(DEFAULT_COMMENT_FR)))
            .andExpect(jsonPath("$.[*].commentGB").value(hasItem(DEFAULT_COMMENT_GB)))
            .andExpect(jsonPath("$.[*].docNameFR").value(hasItem(DEFAULT_DOC_NAME_FR)))
            .andExpect(jsonPath("$.[*].docNameGB").value(hasItem(DEFAULT_DOC_NAME_GB)));
    }
    
    @Test
    @Transactional
    public void getLibCdm() throws Exception {
        // Initialize the database
        libCdmRepository.saveAndFlush(libCdm);

        // Get the libCdm
        restLibCdmMockMvc.perform(get("/api/lib-cdms/{id}", libCdm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(libCdm.getId().intValue()))
            .andExpect(jsonPath("$.nameFR").value(DEFAULT_NAME_FR))
            .andExpect(jsonPath("$.nameGB").value(DEFAULT_NAME_GB))
            .andExpect(jsonPath("$.commentFR").value(DEFAULT_COMMENT_FR))
            .andExpect(jsonPath("$.commentGB").value(DEFAULT_COMMENT_GB))
            .andExpect(jsonPath("$.docNameFR").value(DEFAULT_DOC_NAME_FR))
            .andExpect(jsonPath("$.docNameGB").value(DEFAULT_DOC_NAME_GB));
    }

    @Test
    @Transactional
    public void getNonExistingLibCdm() throws Exception {
        // Get the libCdm
        restLibCdmMockMvc.perform(get("/api/lib-cdms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLibCdm() throws Exception {
        // Initialize the database
        libCdmRepository.saveAndFlush(libCdm);

        int databaseSizeBeforeUpdate = libCdmRepository.findAll().size();

        // Update the libCdm
        LibCdm updatedLibCdm = libCdmRepository.findById(libCdm.getId()).get();
        // Disconnect from session so that the updates on updatedLibCdm are not directly saved in db
        em.detach(updatedLibCdm);
        updatedLibCdm
            .nameFR(UPDATED_NAME_FR)
            .nameGB(UPDATED_NAME_GB)
            .commentFR(UPDATED_COMMENT_FR)
            .commentGB(UPDATED_COMMENT_GB)
            .docNameFR(UPDATED_DOC_NAME_FR)
            .docNameGB(UPDATED_DOC_NAME_GB);

        restLibCdmMockMvc.perform(put("/api/lib-cdms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLibCdm)))
            .andExpect(status().isOk());

        // Validate the LibCdm in the database
        List<LibCdm> libCdmList = libCdmRepository.findAll();
        assertThat(libCdmList).hasSize(databaseSizeBeforeUpdate);
        LibCdm testLibCdm = libCdmList.get(libCdmList.size() - 1);
        assertThat(testLibCdm.getNameFR()).isEqualTo(UPDATED_NAME_FR);
        assertThat(testLibCdm.getNameGB()).isEqualTo(UPDATED_NAME_GB);
        assertThat(testLibCdm.getCommentFR()).isEqualTo(UPDATED_COMMENT_FR);
        assertThat(testLibCdm.getCommentGB()).isEqualTo(UPDATED_COMMENT_GB);
        assertThat(testLibCdm.getDocNameFR()).isEqualTo(UPDATED_DOC_NAME_FR);
        assertThat(testLibCdm.getDocNameGB()).isEqualTo(UPDATED_DOC_NAME_GB);
    }

    @Test
    @Transactional
    public void updateNonExistingLibCdm() throws Exception {
        int databaseSizeBeforeUpdate = libCdmRepository.findAll().size();

        // Create the LibCdm

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLibCdmMockMvc.perform(put("/api/lib-cdms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libCdm)))
            .andExpect(status().isBadRequest());

        // Validate the LibCdm in the database
        List<LibCdm> libCdmList = libCdmRepository.findAll();
        assertThat(libCdmList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLibCdm() throws Exception {
        // Initialize the database
        libCdmRepository.saveAndFlush(libCdm);

        int databaseSizeBeforeDelete = libCdmRepository.findAll().size();

        // Delete the libCdm
        restLibCdmMockMvc.perform(delete("/api/lib-cdms/{id}", libCdm.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LibCdm> libCdmList = libCdmRepository.findAll();
        assertThat(libCdmList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LibCdm.class);
        LibCdm libCdm1 = new LibCdm();
        libCdm1.setId(1L);
        LibCdm libCdm2 = new LibCdm();
        libCdm2.setId(libCdm1.getId());
        assertThat(libCdm1).isEqualTo(libCdm2);
        libCdm2.setId(2L);
        assertThat(libCdm1).isNotEqualTo(libCdm2);
        libCdm1.setId(null);
        assertThat(libCdm1).isNotEqualTo(libCdm2);
    }
}
