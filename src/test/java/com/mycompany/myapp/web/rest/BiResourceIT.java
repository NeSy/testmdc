package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestmdcApp;
import com.mycompany.myapp.domain.Bi;
import com.mycompany.myapp.repository.BiRepository;
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
 * Integration tests for the {@link BiResource} REST controller.
 */
@SpringBootTest(classes = TestmdcApp.class)
public class BiResourceIT {

    private static final String DEFAULT_NAME_FR = "AAAAAAAAAA";
    private static final String UPDATED_NAME_FR = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_GB = "AAAAAAAAAA";
    private static final String UPDATED_NAME_GB = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT_FR = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT_FR = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT_GB = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT_GB = "BBBBBBBBBB";

    private static final String DEFAULT_MNEMONIC_FR = "AAAAAAAAAA";
    private static final String UPDATED_MNEMONIC_FR = "BBBBBBBBBB";

    private static final String DEFAULT_MNEMONIC_GB = "AAAAAAAAAA";
    private static final String UPDATED_MNEMONIC_GB = "BBBBBBBBBB";

    private static final String DEFAULT_CDP = "AAAAAAAAAA";
    private static final String UPDATED_CDP = "BBBBBBBBBB";

    @Autowired
    private BiRepository biRepository;

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

    private MockMvc restBiMockMvc;

    private Bi bi;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BiResource biResource = new BiResource(biRepository);
        this.restBiMockMvc = MockMvcBuilders.standaloneSetup(biResource)
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
    public static Bi createEntity(EntityManager em) {
        Bi bi = new Bi()
            .nameFR(DEFAULT_NAME_FR)
            .nameGB(DEFAULT_NAME_GB)
            .commentFR(DEFAULT_COMMENT_FR)
            .commentGB(DEFAULT_COMMENT_GB)
            .mnemonicFR(DEFAULT_MNEMONIC_FR)
            .mnemonicGB(DEFAULT_MNEMONIC_GB)
            .cdp(DEFAULT_CDP);
        return bi;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bi createUpdatedEntity(EntityManager em) {
        Bi bi = new Bi()
            .nameFR(UPDATED_NAME_FR)
            .nameGB(UPDATED_NAME_GB)
            .commentFR(UPDATED_COMMENT_FR)
            .commentGB(UPDATED_COMMENT_GB)
            .mnemonicFR(UPDATED_MNEMONIC_FR)
            .mnemonicGB(UPDATED_MNEMONIC_GB)
            .cdp(UPDATED_CDP);
        return bi;
    }

    @BeforeEach
    public void initTest() {
        bi = createEntity(em);
    }

    @Test
    @Transactional
    public void createBi() throws Exception {
        int databaseSizeBeforeCreate = biRepository.findAll().size();

        // Create the Bi
        restBiMockMvc.perform(post("/api/bis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bi)))
            .andExpect(status().isCreated());

        // Validate the Bi in the database
        List<Bi> biList = biRepository.findAll();
        assertThat(biList).hasSize(databaseSizeBeforeCreate + 1);
        Bi testBi = biList.get(biList.size() - 1);
        assertThat(testBi.getNameFR()).isEqualTo(DEFAULT_NAME_FR);
        assertThat(testBi.getNameGB()).isEqualTo(DEFAULT_NAME_GB);
        assertThat(testBi.getCommentFR()).isEqualTo(DEFAULT_COMMENT_FR);
        assertThat(testBi.getCommentGB()).isEqualTo(DEFAULT_COMMENT_GB);
        assertThat(testBi.getMnemonicFR()).isEqualTo(DEFAULT_MNEMONIC_FR);
        assertThat(testBi.getMnemonicGB()).isEqualTo(DEFAULT_MNEMONIC_GB);
        assertThat(testBi.getCdp()).isEqualTo(DEFAULT_CDP);
    }

    @Test
    @Transactional
    public void createBiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = biRepository.findAll().size();

        // Create the Bi with an existing ID
        bi.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBiMockMvc.perform(post("/api/bis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bi)))
            .andExpect(status().isBadRequest());

        // Validate the Bi in the database
        List<Bi> biList = biRepository.findAll();
        assertThat(biList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameFRIsRequired() throws Exception {
        int databaseSizeBeforeTest = biRepository.findAll().size();
        // set the field null
        bi.setNameFR(null);

        // Create the Bi, which fails.

        restBiMockMvc.perform(post("/api/bis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bi)))
            .andExpect(status().isBadRequest());

        List<Bi> biList = biRepository.findAll();
        assertThat(biList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCommentFRIsRequired() throws Exception {
        int databaseSizeBeforeTest = biRepository.findAll().size();
        // set the field null
        bi.setCommentFR(null);

        // Create the Bi, which fails.

        restBiMockMvc.perform(post("/api/bis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bi)))
            .andExpect(status().isBadRequest());

        List<Bi> biList = biRepository.findAll();
        assertThat(biList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMnemonicFRIsRequired() throws Exception {
        int databaseSizeBeforeTest = biRepository.findAll().size();
        // set the field null
        bi.setMnemonicFR(null);

        // Create the Bi, which fails.

        restBiMockMvc.perform(post("/api/bis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bi)))
            .andExpect(status().isBadRequest());

        List<Bi> biList = biRepository.findAll();
        assertThat(biList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBis() throws Exception {
        // Initialize the database
        biRepository.saveAndFlush(bi);

        // Get all the biList
        restBiMockMvc.perform(get("/api/bis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bi.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameFR").value(hasItem(DEFAULT_NAME_FR)))
            .andExpect(jsonPath("$.[*].nameGB").value(hasItem(DEFAULT_NAME_GB)))
            .andExpect(jsonPath("$.[*].commentFR").value(hasItem(DEFAULT_COMMENT_FR)))
            .andExpect(jsonPath("$.[*].commentGB").value(hasItem(DEFAULT_COMMENT_GB)))
            .andExpect(jsonPath("$.[*].mnemonicFR").value(hasItem(DEFAULT_MNEMONIC_FR)))
            .andExpect(jsonPath("$.[*].mnemonicGB").value(hasItem(DEFAULT_MNEMONIC_GB)))
            .andExpect(jsonPath("$.[*].cdp").value(hasItem(DEFAULT_CDP)));
    }
    
    @Test
    @Transactional
    public void getBi() throws Exception {
        // Initialize the database
        biRepository.saveAndFlush(bi);

        // Get the bi
        restBiMockMvc.perform(get("/api/bis/{id}", bi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bi.getId().intValue()))
            .andExpect(jsonPath("$.nameFR").value(DEFAULT_NAME_FR))
            .andExpect(jsonPath("$.nameGB").value(DEFAULT_NAME_GB))
            .andExpect(jsonPath("$.commentFR").value(DEFAULT_COMMENT_FR))
            .andExpect(jsonPath("$.commentGB").value(DEFAULT_COMMENT_GB))
            .andExpect(jsonPath("$.mnemonicFR").value(DEFAULT_MNEMONIC_FR))
            .andExpect(jsonPath("$.mnemonicGB").value(DEFAULT_MNEMONIC_GB))
            .andExpect(jsonPath("$.cdp").value(DEFAULT_CDP));
    }

    @Test
    @Transactional
    public void getNonExistingBi() throws Exception {
        // Get the bi
        restBiMockMvc.perform(get("/api/bis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBi() throws Exception {
        // Initialize the database
        biRepository.saveAndFlush(bi);

        int databaseSizeBeforeUpdate = biRepository.findAll().size();

        // Update the bi
        Bi updatedBi = biRepository.findById(bi.getId()).get();
        // Disconnect from session so that the updates on updatedBi are not directly saved in db
        em.detach(updatedBi);
        updatedBi
            .nameFR(UPDATED_NAME_FR)
            .nameGB(UPDATED_NAME_GB)
            .commentFR(UPDATED_COMMENT_FR)
            .commentGB(UPDATED_COMMENT_GB)
            .mnemonicFR(UPDATED_MNEMONIC_FR)
            .mnemonicGB(UPDATED_MNEMONIC_GB)
            .cdp(UPDATED_CDP);

        restBiMockMvc.perform(put("/api/bis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBi)))
            .andExpect(status().isOk());

        // Validate the Bi in the database
        List<Bi> biList = biRepository.findAll();
        assertThat(biList).hasSize(databaseSizeBeforeUpdate);
        Bi testBi = biList.get(biList.size() - 1);
        assertThat(testBi.getNameFR()).isEqualTo(UPDATED_NAME_FR);
        assertThat(testBi.getNameGB()).isEqualTo(UPDATED_NAME_GB);
        assertThat(testBi.getCommentFR()).isEqualTo(UPDATED_COMMENT_FR);
        assertThat(testBi.getCommentGB()).isEqualTo(UPDATED_COMMENT_GB);
        assertThat(testBi.getMnemonicFR()).isEqualTo(UPDATED_MNEMONIC_FR);
        assertThat(testBi.getMnemonicGB()).isEqualTo(UPDATED_MNEMONIC_GB);
        assertThat(testBi.getCdp()).isEqualTo(UPDATED_CDP);
    }

    @Test
    @Transactional
    public void updateNonExistingBi() throws Exception {
        int databaseSizeBeforeUpdate = biRepository.findAll().size();

        // Create the Bi

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBiMockMvc.perform(put("/api/bis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bi)))
            .andExpect(status().isBadRequest());

        // Validate the Bi in the database
        List<Bi> biList = biRepository.findAll();
        assertThat(biList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBi() throws Exception {
        // Initialize the database
        biRepository.saveAndFlush(bi);

        int databaseSizeBeforeDelete = biRepository.findAll().size();

        // Delete the bi
        restBiMockMvc.perform(delete("/api/bis/{id}", bi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bi> biList = biRepository.findAll();
        assertThat(biList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bi.class);
        Bi bi1 = new Bi();
        bi1.setId(1L);
        Bi bi2 = new Bi();
        bi2.setId(bi1.getId());
        assertThat(bi1).isEqualTo(bi2);
        bi2.setId(2L);
        assertThat(bi1).isNotEqualTo(bi2);
        bi1.setId(null);
        assertThat(bi1).isNotEqualTo(bi2);
    }
}
