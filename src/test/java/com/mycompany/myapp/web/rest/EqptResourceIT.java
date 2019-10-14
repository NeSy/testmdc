package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestmdcApp;
import com.mycompany.myapp.domain.Eqpt;
import com.mycompany.myapp.repository.EqptRepository;
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

import com.mycompany.myapp.domain.enumeration.EqptType;
/**
 * Integration tests for the {@link EqptResource} REST controller.
 */
@SpringBootTest(classes = TestmdcApp.class)
public class EqptResourceIT {

    private static final EqptType DEFAULT_TYPE = EqptType.SA;
    private static final EqptType UPDATED_TYPE = EqptType.S;

    private static final String DEFAULT_NAME_FR = "AAAAAAAAAA";
    private static final String UPDATED_NAME_FR = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_GB = "AAAAAAAAAA";
    private static final String UPDATED_NAME_GB = "BBBBBBBBBB";

    private static final String DEFAULT_MNEMONIC_FR = "AAAAAAAAAA";
    private static final String UPDATED_MNEMONIC_FR = "BBBBBBBBBB";

    private static final String DEFAULT_MNEMONIC_GB = "AAAAAAAAAA";
    private static final String UPDATED_MNEMONIC_GB = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT_FR = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT_FR = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT_GB = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT_GB = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "63";
    private static final String UPDATED_ADDRESS = "16";

    private static final String DEFAULT_KIND = "AAAAAAAAAA";
    private static final String UPDATED_KIND = "BBBBBBBBBB";

    private static final String DEFAULT_CDP = "AAAAAAAAAA";
    private static final String UPDATED_CDP = "BBBBBBBBBB";

    @Autowired
    private EqptRepository eqptRepository;

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

    private MockMvc restEqptMockMvc;

    private Eqpt eqpt;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EqptResource eqptResource = new EqptResource(eqptRepository);
        this.restEqptMockMvc = MockMvcBuilders.standaloneSetup(eqptResource)
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
    public static Eqpt createEntity(EntityManager em) {
        Eqpt eqpt = new Eqpt()
            .type(DEFAULT_TYPE)
            .nameFR(DEFAULT_NAME_FR)
            .nameGB(DEFAULT_NAME_GB)
            .mnemonicFR(DEFAULT_MNEMONIC_FR)
            .mnemonicGB(DEFAULT_MNEMONIC_GB)
            .commentFR(DEFAULT_COMMENT_FR)
            .commentGB(DEFAULT_COMMENT_GB)
            .address(DEFAULT_ADDRESS)
            .kind(DEFAULT_KIND)
            .cdp(DEFAULT_CDP);
        return eqpt;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eqpt createUpdatedEntity(EntityManager em) {
        Eqpt eqpt = new Eqpt()
            .type(UPDATED_TYPE)
            .nameFR(UPDATED_NAME_FR)
            .nameGB(UPDATED_NAME_GB)
            .mnemonicFR(UPDATED_MNEMONIC_FR)
            .mnemonicGB(UPDATED_MNEMONIC_GB)
            .commentFR(UPDATED_COMMENT_FR)
            .commentGB(UPDATED_COMMENT_GB)
            .address(UPDATED_ADDRESS)
            .kind(UPDATED_KIND)
            .cdp(UPDATED_CDP);
        return eqpt;
    }

    @BeforeEach
    public void initTest() {
        eqpt = createEntity(em);
    }

    @Test
    @Transactional
    public void createEqpt() throws Exception {
        int databaseSizeBeforeCreate = eqptRepository.findAll().size();

        // Create the Eqpt
        restEqptMockMvc.perform(post("/api/eqpts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eqpt)))
            .andExpect(status().isCreated());

        // Validate the Eqpt in the database
        List<Eqpt> eqptList = eqptRepository.findAll();
        assertThat(eqptList).hasSize(databaseSizeBeforeCreate + 1);
        Eqpt testEqpt = eqptList.get(eqptList.size() - 1);
        assertThat(testEqpt.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEqpt.getNameFR()).isEqualTo(DEFAULT_NAME_FR);
        assertThat(testEqpt.getNameGB()).isEqualTo(DEFAULT_NAME_GB);
        assertThat(testEqpt.getMnemonicFR()).isEqualTo(DEFAULT_MNEMONIC_FR);
        assertThat(testEqpt.getMnemonicGB()).isEqualTo(DEFAULT_MNEMONIC_GB);
        assertThat(testEqpt.getCommentFR()).isEqualTo(DEFAULT_COMMENT_FR);
        assertThat(testEqpt.getCommentGB()).isEqualTo(DEFAULT_COMMENT_GB);
        assertThat(testEqpt.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testEqpt.getKind()).isEqualTo(DEFAULT_KIND);
        assertThat(testEqpt.getCdp()).isEqualTo(DEFAULT_CDP);
    }

    @Test
    @Transactional
    public void createEqptWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eqptRepository.findAll().size();

        // Create the Eqpt with an existing ID
        eqpt.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEqptMockMvc.perform(post("/api/eqpts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eqpt)))
            .andExpect(status().isBadRequest());

        // Validate the Eqpt in the database
        List<Eqpt> eqptList = eqptRepository.findAll();
        assertThat(eqptList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = eqptRepository.findAll().size();
        // set the field null
        eqpt.setType(null);

        // Create the Eqpt, which fails.

        restEqptMockMvc.perform(post("/api/eqpts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eqpt)))
            .andExpect(status().isBadRequest());

        List<Eqpt> eqptList = eqptRepository.findAll();
        assertThat(eqptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameFRIsRequired() throws Exception {
        int databaseSizeBeforeTest = eqptRepository.findAll().size();
        // set the field null
        eqpt.setNameFR(null);

        // Create the Eqpt, which fails.

        restEqptMockMvc.perform(post("/api/eqpts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eqpt)))
            .andExpect(status().isBadRequest());

        List<Eqpt> eqptList = eqptRepository.findAll();
        assertThat(eqptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMnemonicFRIsRequired() throws Exception {
        int databaseSizeBeforeTest = eqptRepository.findAll().size();
        // set the field null
        eqpt.setMnemonicFR(null);

        // Create the Eqpt, which fails.

        restEqptMockMvc.perform(post("/api/eqpts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eqpt)))
            .andExpect(status().isBadRequest());

        List<Eqpt> eqptList = eqptRepository.findAll();
        assertThat(eqptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCommentFRIsRequired() throws Exception {
        int databaseSizeBeforeTest = eqptRepository.findAll().size();
        // set the field null
        eqpt.setCommentFR(null);

        // Create the Eqpt, which fails.

        restEqptMockMvc.perform(post("/api/eqpts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eqpt)))
            .andExpect(status().isBadRequest());

        List<Eqpt> eqptList = eqptRepository.findAll();
        assertThat(eqptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = eqptRepository.findAll().size();
        // set the field null
        eqpt.setAddress(null);

        // Create the Eqpt, which fails.

        restEqptMockMvc.perform(post("/api/eqpts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eqpt)))
            .andExpect(status().isBadRequest());

        List<Eqpt> eqptList = eqptRepository.findAll();
        assertThat(eqptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKindIsRequired() throws Exception {
        int databaseSizeBeforeTest = eqptRepository.findAll().size();
        // set the field null
        eqpt.setKind(null);

        // Create the Eqpt, which fails.

        restEqptMockMvc.perform(post("/api/eqpts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eqpt)))
            .andExpect(status().isBadRequest());

        List<Eqpt> eqptList = eqptRepository.findAll();
        assertThat(eqptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEqpts() throws Exception {
        // Initialize the database
        eqptRepository.saveAndFlush(eqpt);

        // Get all the eqptList
        restEqptMockMvc.perform(get("/api/eqpts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eqpt.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].nameFR").value(hasItem(DEFAULT_NAME_FR)))
            .andExpect(jsonPath("$.[*].nameGB").value(hasItem(DEFAULT_NAME_GB)))
            .andExpect(jsonPath("$.[*].mnemonicFR").value(hasItem(DEFAULT_MNEMONIC_FR)))
            .andExpect(jsonPath("$.[*].mnemonicGB").value(hasItem(DEFAULT_MNEMONIC_GB)))
            .andExpect(jsonPath("$.[*].commentFR").value(hasItem(DEFAULT_COMMENT_FR)))
            .andExpect(jsonPath("$.[*].commentGB").value(hasItem(DEFAULT_COMMENT_GB)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].kind").value(hasItem(DEFAULT_KIND)))
            .andExpect(jsonPath("$.[*].cdp").value(hasItem(DEFAULT_CDP)));
    }
    
    @Test
    @Transactional
    public void getEqpt() throws Exception {
        // Initialize the database
        eqptRepository.saveAndFlush(eqpt);

        // Get the eqpt
        restEqptMockMvc.perform(get("/api/eqpts/{id}", eqpt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eqpt.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.nameFR").value(DEFAULT_NAME_FR))
            .andExpect(jsonPath("$.nameGB").value(DEFAULT_NAME_GB))
            .andExpect(jsonPath("$.mnemonicFR").value(DEFAULT_MNEMONIC_FR))
            .andExpect(jsonPath("$.mnemonicGB").value(DEFAULT_MNEMONIC_GB))
            .andExpect(jsonPath("$.commentFR").value(DEFAULT_COMMENT_FR))
            .andExpect(jsonPath("$.commentGB").value(DEFAULT_COMMENT_GB))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.kind").value(DEFAULT_KIND))
            .andExpect(jsonPath("$.cdp").value(DEFAULT_CDP));
    }

    @Test
    @Transactional
    public void getNonExistingEqpt() throws Exception {
        // Get the eqpt
        restEqptMockMvc.perform(get("/api/eqpts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEqpt() throws Exception {
        // Initialize the database
        eqptRepository.saveAndFlush(eqpt);

        int databaseSizeBeforeUpdate = eqptRepository.findAll().size();

        // Update the eqpt
        Eqpt updatedEqpt = eqptRepository.findById(eqpt.getId()).get();
        // Disconnect from session so that the updates on updatedEqpt are not directly saved in db
        em.detach(updatedEqpt);
        updatedEqpt
            .type(UPDATED_TYPE)
            .nameFR(UPDATED_NAME_FR)
            .nameGB(UPDATED_NAME_GB)
            .mnemonicFR(UPDATED_MNEMONIC_FR)
            .mnemonicGB(UPDATED_MNEMONIC_GB)
            .commentFR(UPDATED_COMMENT_FR)
            .commentGB(UPDATED_COMMENT_GB)
            .address(UPDATED_ADDRESS)
            .kind(UPDATED_KIND)
            .cdp(UPDATED_CDP);

        restEqptMockMvc.perform(put("/api/eqpts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEqpt)))
            .andExpect(status().isOk());

        // Validate the Eqpt in the database
        List<Eqpt> eqptList = eqptRepository.findAll();
        assertThat(eqptList).hasSize(databaseSizeBeforeUpdate);
        Eqpt testEqpt = eqptList.get(eqptList.size() - 1);
        assertThat(testEqpt.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEqpt.getNameFR()).isEqualTo(UPDATED_NAME_FR);
        assertThat(testEqpt.getNameGB()).isEqualTo(UPDATED_NAME_GB);
        assertThat(testEqpt.getMnemonicFR()).isEqualTo(UPDATED_MNEMONIC_FR);
        assertThat(testEqpt.getMnemonicGB()).isEqualTo(UPDATED_MNEMONIC_GB);
        assertThat(testEqpt.getCommentFR()).isEqualTo(UPDATED_COMMENT_FR);
        assertThat(testEqpt.getCommentGB()).isEqualTo(UPDATED_COMMENT_GB);
        assertThat(testEqpt.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testEqpt.getKind()).isEqualTo(UPDATED_KIND);
        assertThat(testEqpt.getCdp()).isEqualTo(UPDATED_CDP);
    }

    @Test
    @Transactional
    public void updateNonExistingEqpt() throws Exception {
        int databaseSizeBeforeUpdate = eqptRepository.findAll().size();

        // Create the Eqpt

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEqptMockMvc.perform(put("/api/eqpts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eqpt)))
            .andExpect(status().isBadRequest());

        // Validate the Eqpt in the database
        List<Eqpt> eqptList = eqptRepository.findAll();
        assertThat(eqptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEqpt() throws Exception {
        // Initialize the database
        eqptRepository.saveAndFlush(eqpt);

        int databaseSizeBeforeDelete = eqptRepository.findAll().size();

        // Delete the eqpt
        restEqptMockMvc.perform(delete("/api/eqpts/{id}", eqpt.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Eqpt> eqptList = eqptRepository.findAll();
        assertThat(eqptList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Eqpt.class);
        Eqpt eqpt1 = new Eqpt();
        eqpt1.setId(1L);
        Eqpt eqpt2 = new Eqpt();
        eqpt2.setId(eqpt1.getId());
        assertThat(eqpt1).isEqualTo(eqpt2);
        eqpt2.setId(2L);
        assertThat(eqpt1).isNotEqualTo(eqpt2);
        eqpt1.setId(null);
        assertThat(eqpt1).isNotEqualTo(eqpt2);
    }
}
