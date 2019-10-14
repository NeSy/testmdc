package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestmdcApp;
import com.mycompany.myapp.domain.PointEmport;
import com.mycompany.myapp.repository.PointEmportRepository;
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
 * Integration tests for the {@link PointEmportResource} REST controller.
 */
@SpringBootTest(classes = TestmdcApp.class)
public class PointEmportResourceIT {

    private static final String DEFAULT_NAME_FR = "AAAAAAAAAA";
    private static final String UPDATED_NAME_FR = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_GB = "AAAAAAAAAA";
    private static final String UPDATED_NAME_GB = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT_FR = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT_FR = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT_GB = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT_GB = "BBBBBBBBBB";

    private static final String DEFAULT_MNEMONIC_GB = "AAAAAAAAAA";
    private static final String UPDATED_MNEMONIC_GB = "BBBBBBBBBB";

    @Autowired
    private PointEmportRepository pointEmportRepository;

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

    private MockMvc restPointEmportMockMvc;

    private PointEmport pointEmport;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PointEmportResource pointEmportResource = new PointEmportResource(pointEmportRepository);
        this.restPointEmportMockMvc = MockMvcBuilders.standaloneSetup(pointEmportResource)
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
    public static PointEmport createEntity(EntityManager em) {
        PointEmport pointEmport = new PointEmport()
            .nameFR(DEFAULT_NAME_FR)
            .nameGB(DEFAULT_NAME_GB)
            .commentFR(DEFAULT_COMMENT_FR)
            .commentGB(DEFAULT_COMMENT_GB)
            .mnemonicGB(DEFAULT_MNEMONIC_GB);
        return pointEmport;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PointEmport createUpdatedEntity(EntityManager em) {
        PointEmport pointEmport = new PointEmport()
            .nameFR(UPDATED_NAME_FR)
            .nameGB(UPDATED_NAME_GB)
            .commentFR(UPDATED_COMMENT_FR)
            .commentGB(UPDATED_COMMENT_GB)
            .mnemonicGB(UPDATED_MNEMONIC_GB);
        return pointEmport;
    }

    @BeforeEach
    public void initTest() {
        pointEmport = createEntity(em);
    }

    @Test
    @Transactional
    public void createPointEmport() throws Exception {
        int databaseSizeBeforeCreate = pointEmportRepository.findAll().size();

        // Create the PointEmport
        restPointEmportMockMvc.perform(post("/api/point-emports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pointEmport)))
            .andExpect(status().isCreated());

        // Validate the PointEmport in the database
        List<PointEmport> pointEmportList = pointEmportRepository.findAll();
        assertThat(pointEmportList).hasSize(databaseSizeBeforeCreate + 1);
        PointEmport testPointEmport = pointEmportList.get(pointEmportList.size() - 1);
        assertThat(testPointEmport.getNameFR()).isEqualTo(DEFAULT_NAME_FR);
        assertThat(testPointEmport.getNameGB()).isEqualTo(DEFAULT_NAME_GB);
        assertThat(testPointEmport.getCommentFR()).isEqualTo(DEFAULT_COMMENT_FR);
        assertThat(testPointEmport.getCommentGB()).isEqualTo(DEFAULT_COMMENT_GB);
        assertThat(testPointEmport.getMnemonicGB()).isEqualTo(DEFAULT_MNEMONIC_GB);
    }

    @Test
    @Transactional
    public void createPointEmportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pointEmportRepository.findAll().size();

        // Create the PointEmport with an existing ID
        pointEmport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPointEmportMockMvc.perform(post("/api/point-emports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pointEmport)))
            .andExpect(status().isBadRequest());

        // Validate the PointEmport in the database
        List<PointEmport> pointEmportList = pointEmportRepository.findAll();
        assertThat(pointEmportList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameFRIsRequired() throws Exception {
        int databaseSizeBeforeTest = pointEmportRepository.findAll().size();
        // set the field null
        pointEmport.setNameFR(null);

        // Create the PointEmport, which fails.

        restPointEmportMockMvc.perform(post("/api/point-emports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pointEmport)))
            .andExpect(status().isBadRequest());

        List<PointEmport> pointEmportList = pointEmportRepository.findAll();
        assertThat(pointEmportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCommentFRIsRequired() throws Exception {
        int databaseSizeBeforeTest = pointEmportRepository.findAll().size();
        // set the field null
        pointEmport.setCommentFR(null);

        // Create the PointEmport, which fails.

        restPointEmportMockMvc.perform(post("/api/point-emports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pointEmport)))
            .andExpect(status().isBadRequest());

        List<PointEmport> pointEmportList = pointEmportRepository.findAll();
        assertThat(pointEmportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPointEmports() throws Exception {
        // Initialize the database
        pointEmportRepository.saveAndFlush(pointEmport);

        // Get all the pointEmportList
        restPointEmportMockMvc.perform(get("/api/point-emports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pointEmport.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameFR").value(hasItem(DEFAULT_NAME_FR)))
            .andExpect(jsonPath("$.[*].nameGB").value(hasItem(DEFAULT_NAME_GB)))
            .andExpect(jsonPath("$.[*].commentFR").value(hasItem(DEFAULT_COMMENT_FR)))
            .andExpect(jsonPath("$.[*].commentGB").value(hasItem(DEFAULT_COMMENT_GB)))
            .andExpect(jsonPath("$.[*].mnemonicGB").value(hasItem(DEFAULT_MNEMONIC_GB)));
    }
    
    @Test
    @Transactional
    public void getPointEmport() throws Exception {
        // Initialize the database
        pointEmportRepository.saveAndFlush(pointEmport);

        // Get the pointEmport
        restPointEmportMockMvc.perform(get("/api/point-emports/{id}", pointEmport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pointEmport.getId().intValue()))
            .andExpect(jsonPath("$.nameFR").value(DEFAULT_NAME_FR))
            .andExpect(jsonPath("$.nameGB").value(DEFAULT_NAME_GB))
            .andExpect(jsonPath("$.commentFR").value(DEFAULT_COMMENT_FR))
            .andExpect(jsonPath("$.commentGB").value(DEFAULT_COMMENT_GB))
            .andExpect(jsonPath("$.mnemonicGB").value(DEFAULT_MNEMONIC_GB));
    }

    @Test
    @Transactional
    public void getNonExistingPointEmport() throws Exception {
        // Get the pointEmport
        restPointEmportMockMvc.perform(get("/api/point-emports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePointEmport() throws Exception {
        // Initialize the database
        pointEmportRepository.saveAndFlush(pointEmport);

        int databaseSizeBeforeUpdate = pointEmportRepository.findAll().size();

        // Update the pointEmport
        PointEmport updatedPointEmport = pointEmportRepository.findById(pointEmport.getId()).get();
        // Disconnect from session so that the updates on updatedPointEmport are not directly saved in db
        em.detach(updatedPointEmport);
        updatedPointEmport
            .nameFR(UPDATED_NAME_FR)
            .nameGB(UPDATED_NAME_GB)
            .commentFR(UPDATED_COMMENT_FR)
            .commentGB(UPDATED_COMMENT_GB)
            .mnemonicGB(UPDATED_MNEMONIC_GB);

        restPointEmportMockMvc.perform(put("/api/point-emports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPointEmport)))
            .andExpect(status().isOk());

        // Validate the PointEmport in the database
        List<PointEmport> pointEmportList = pointEmportRepository.findAll();
        assertThat(pointEmportList).hasSize(databaseSizeBeforeUpdate);
        PointEmport testPointEmport = pointEmportList.get(pointEmportList.size() - 1);
        assertThat(testPointEmport.getNameFR()).isEqualTo(UPDATED_NAME_FR);
        assertThat(testPointEmport.getNameGB()).isEqualTo(UPDATED_NAME_GB);
        assertThat(testPointEmport.getCommentFR()).isEqualTo(UPDATED_COMMENT_FR);
        assertThat(testPointEmport.getCommentGB()).isEqualTo(UPDATED_COMMENT_GB);
        assertThat(testPointEmport.getMnemonicGB()).isEqualTo(UPDATED_MNEMONIC_GB);
    }

    @Test
    @Transactional
    public void updateNonExistingPointEmport() throws Exception {
        int databaseSizeBeforeUpdate = pointEmportRepository.findAll().size();

        // Create the PointEmport

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPointEmportMockMvc.perform(put("/api/point-emports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pointEmport)))
            .andExpect(status().isBadRequest());

        // Validate the PointEmport in the database
        List<PointEmport> pointEmportList = pointEmportRepository.findAll();
        assertThat(pointEmportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePointEmport() throws Exception {
        // Initialize the database
        pointEmportRepository.saveAndFlush(pointEmport);

        int databaseSizeBeforeDelete = pointEmportRepository.findAll().size();

        // Delete the pointEmport
        restPointEmportMockMvc.perform(delete("/api/point-emports/{id}", pointEmport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PointEmport> pointEmportList = pointEmportRepository.findAll();
        assertThat(pointEmportList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PointEmport.class);
        PointEmport pointEmport1 = new PointEmport();
        pointEmport1.setId(1L);
        PointEmport pointEmport2 = new PointEmport();
        pointEmport2.setId(pointEmport1.getId());
        assertThat(pointEmport1).isEqualTo(pointEmport2);
        pointEmport2.setId(2L);
        assertThat(pointEmport1).isNotEqualTo(pointEmport2);
        pointEmport1.setId(null);
        assertThat(pointEmport1).isNotEqualTo(pointEmport2);
    }
}
