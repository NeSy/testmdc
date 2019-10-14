package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestmdcApp;
import com.mycompany.myapp.domain.Cdm;
import com.mycompany.myapp.repository.CdmRepository;
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

import com.mycompany.myapp.domain.enumeration.CdmNature;
import com.mycompany.myapp.domain.enumeration.EqptKind;
/**
 * Integration tests for the {@link CdmResource} REST controller.
 */
@SpringBootTest(classes = TestmdcApp.class)
public class CdmResourceIT {

    private static final String DEFAULT_INDEX = "11";
    private static final String UPDATED_INDEX = "26";

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

    private static final String DEFAULT_BUS_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_BUS_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_BUS_WORD = "AAAAAAAAAA";
    private static final String UPDATED_BUS_WORD = "BBBBBBBBBB";

    private static final String DEFAULT_MNEMONIC_FR = "AAAAAAAAAA";
    private static final String UPDATED_MNEMONIC_FR = "BBBBBBBBBB";

    private static final Integer DEFAULT_OFFSET = 1;
    private static final Integer UPDATED_OFFSET = 2;

    private static final String DEFAULT_CODING = "AAAAAAAAAA";
    private static final String UPDATED_CODING = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT_MSG = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_MSG = "BBBBBBBBBB";

    private static final String DEFAULT_MIN_MSG = "AAAAAAAAAA";
    private static final String UPDATED_MIN_MSG = "BBBBBBBBBB";

    private static final String DEFAULT_MAX_MSG = "AAAAAAAAAA";
    private static final String UPDATED_MAX_MSG = "BBBBBBBBBB";

    private static final CdmNature DEFAULT_NATURE = CdmNature.DISCRETE;
    private static final CdmNature UPDATED_NATURE = CdmNature.DIGITAL;

    private static final Boolean DEFAULT_SIGN = false;
    private static final Boolean UPDATED_SIGN = true;

    private static final String DEFAULT_CADRAGE_VTL = "AAAAAAAAAA";
    private static final String UPDATED_CADRAGE_VTL = "BBBBBBBBBB";

    private static final String DEFAULT_MIN_VALUE_VTL = "AAAAAAAAAA";
    private static final String UPDATED_MIN_VALUE_VTL = "BBBBBBBBBB";

    private static final String DEFAULT_MAX_VALUE_VTL = "AAAAAAAAAA";
    private static final String UPDATED_MAX_VALUE_VTL = "BBBBBBBBBB";

    private static final Integer DEFAULT_MIN_BYTE_VTL = 1;
    private static final Integer UPDATED_MIN_BYTE_VTL = 2;

    private static final Integer DEFAULT_MAX_BYTE_VTL = 1;
    private static final Integer UPDATED_MAX_BYTE_VTL = 2;

    private static final String DEFAULT_UNIT_VTL = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_VTL = "BBBBBBBBBB";

    private static final EqptKind DEFAULT_KIND = EqptKind.R;
    private static final EqptKind UPDATED_KIND = EqptKind.S;

    private static final Boolean DEFAULT_LINEAR = false;
    private static final Boolean UPDATED_LINEAR = true;

    private static final Integer DEFAULT_FUNC = 1;
    private static final Integer UPDATED_FUNC = 2;

    @Autowired
    private CdmRepository cdmRepository;

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

    private MockMvc restCdmMockMvc;

    private Cdm cdm;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CdmResource cdmResource = new CdmResource(cdmRepository);
        this.restCdmMockMvc = MockMvcBuilders.standaloneSetup(cdmResource)
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
    public static Cdm createEntity(EntityManager em) {
        Cdm cdm = new Cdm()
            .index(DEFAULT_INDEX)
            .nameFR(DEFAULT_NAME_FR)
            .nameGB(DEFAULT_NAME_GB)
            .commentFR(DEFAULT_COMMENT_FR)
            .commentGB(DEFAULT_COMMENT_GB)
            .docNameFR(DEFAULT_DOC_NAME_FR)
            .docNameGB(DEFAULT_DOC_NAME_GB)
            .busMessage(DEFAULT_BUS_MESSAGE)
            .busWord(DEFAULT_BUS_WORD)
            .mnemonicFR(DEFAULT_MNEMONIC_FR)
            .offset(DEFAULT_OFFSET)
            .coding(DEFAULT_CODING)
            .unitMsg(DEFAULT_UNIT_MSG)
            .minMsg(DEFAULT_MIN_MSG)
            .maxMsg(DEFAULT_MAX_MSG)
            .nature(DEFAULT_NATURE)
            .sign(DEFAULT_SIGN)
            .cadrageVTL(DEFAULT_CADRAGE_VTL)
            .minValueVTL(DEFAULT_MIN_VALUE_VTL)
            .maxValueVTL(DEFAULT_MAX_VALUE_VTL)
            .minByteVTL(DEFAULT_MIN_BYTE_VTL)
            .maxByteVTL(DEFAULT_MAX_BYTE_VTL)
            .unitVTL(DEFAULT_UNIT_VTL)
            .kind(DEFAULT_KIND)
            .linear(DEFAULT_LINEAR)
            .func(DEFAULT_FUNC);
        return cdm;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cdm createUpdatedEntity(EntityManager em) {
        Cdm cdm = new Cdm()
            .index(UPDATED_INDEX)
            .nameFR(UPDATED_NAME_FR)
            .nameGB(UPDATED_NAME_GB)
            .commentFR(UPDATED_COMMENT_FR)
            .commentGB(UPDATED_COMMENT_GB)
            .docNameFR(UPDATED_DOC_NAME_FR)
            .docNameGB(UPDATED_DOC_NAME_GB)
            .busMessage(UPDATED_BUS_MESSAGE)
            .busWord(UPDATED_BUS_WORD)
            .mnemonicFR(UPDATED_MNEMONIC_FR)
            .offset(UPDATED_OFFSET)
            .coding(UPDATED_CODING)
            .unitMsg(UPDATED_UNIT_MSG)
            .minMsg(UPDATED_MIN_MSG)
            .maxMsg(UPDATED_MAX_MSG)
            .nature(UPDATED_NATURE)
            .sign(UPDATED_SIGN)
            .cadrageVTL(UPDATED_CADRAGE_VTL)
            .minValueVTL(UPDATED_MIN_VALUE_VTL)
            .maxValueVTL(UPDATED_MAX_VALUE_VTL)
            .minByteVTL(UPDATED_MIN_BYTE_VTL)
            .maxByteVTL(UPDATED_MAX_BYTE_VTL)
            .unitVTL(UPDATED_UNIT_VTL)
            .kind(UPDATED_KIND)
            .linear(UPDATED_LINEAR)
            .func(UPDATED_FUNC);
        return cdm;
    }

    @BeforeEach
    public void initTest() {
        cdm = createEntity(em);
    }

    @Test
    @Transactional
    public void createCdm() throws Exception {
        int databaseSizeBeforeCreate = cdmRepository.findAll().size();

        // Create the Cdm
        restCdmMockMvc.perform(post("/api/cdms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cdm)))
            .andExpect(status().isCreated());

        // Validate the Cdm in the database
        List<Cdm> cdmList = cdmRepository.findAll();
        assertThat(cdmList).hasSize(databaseSizeBeforeCreate + 1);
        Cdm testCdm = cdmList.get(cdmList.size() - 1);
        assertThat(testCdm.getIndex()).isEqualTo(DEFAULT_INDEX);
        assertThat(testCdm.getNameFR()).isEqualTo(DEFAULT_NAME_FR);
        assertThat(testCdm.getNameGB()).isEqualTo(DEFAULT_NAME_GB);
        assertThat(testCdm.getCommentFR()).isEqualTo(DEFAULT_COMMENT_FR);
        assertThat(testCdm.getCommentGB()).isEqualTo(DEFAULT_COMMENT_GB);
        assertThat(testCdm.getDocNameFR()).isEqualTo(DEFAULT_DOC_NAME_FR);
        assertThat(testCdm.getDocNameGB()).isEqualTo(DEFAULT_DOC_NAME_GB);
        assertThat(testCdm.getBusMessage()).isEqualTo(DEFAULT_BUS_MESSAGE);
        assertThat(testCdm.getBusWord()).isEqualTo(DEFAULT_BUS_WORD);
        assertThat(testCdm.getMnemonicFR()).isEqualTo(DEFAULT_MNEMONIC_FR);
        assertThat(testCdm.getOffset()).isEqualTo(DEFAULT_OFFSET);
        assertThat(testCdm.getCoding()).isEqualTo(DEFAULT_CODING);
        assertThat(testCdm.getUnitMsg()).isEqualTo(DEFAULT_UNIT_MSG);
        assertThat(testCdm.getMinMsg()).isEqualTo(DEFAULT_MIN_MSG);
        assertThat(testCdm.getMaxMsg()).isEqualTo(DEFAULT_MAX_MSG);
        assertThat(testCdm.getNature()).isEqualTo(DEFAULT_NATURE);
        assertThat(testCdm.isSign()).isEqualTo(DEFAULT_SIGN);
        assertThat(testCdm.getCadrageVTL()).isEqualTo(DEFAULT_CADRAGE_VTL);
        assertThat(testCdm.getMinValueVTL()).isEqualTo(DEFAULT_MIN_VALUE_VTL);
        assertThat(testCdm.getMaxValueVTL()).isEqualTo(DEFAULT_MAX_VALUE_VTL);
        assertThat(testCdm.getMinByteVTL()).isEqualTo(DEFAULT_MIN_BYTE_VTL);
        assertThat(testCdm.getMaxByteVTL()).isEqualTo(DEFAULT_MAX_BYTE_VTL);
        assertThat(testCdm.getUnitVTL()).isEqualTo(DEFAULT_UNIT_VTL);
        assertThat(testCdm.getKind()).isEqualTo(DEFAULT_KIND);
        assertThat(testCdm.isLinear()).isEqualTo(DEFAULT_LINEAR);
        assertThat(testCdm.getFunc()).isEqualTo(DEFAULT_FUNC);
    }

    @Test
    @Transactional
    public void createCdmWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cdmRepository.findAll().size();

        // Create the Cdm with an existing ID
        cdm.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCdmMockMvc.perform(post("/api/cdms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cdm)))
            .andExpect(status().isBadRequest());

        // Validate the Cdm in the database
        List<Cdm> cdmList = cdmRepository.findAll();
        assertThat(cdmList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIndexIsRequired() throws Exception {
        int databaseSizeBeforeTest = cdmRepository.findAll().size();
        // set the field null
        cdm.setIndex(null);

        // Create the Cdm, which fails.

        restCdmMockMvc.perform(post("/api/cdms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cdm)))
            .andExpect(status().isBadRequest());

        List<Cdm> cdmList = cdmRepository.findAll();
        assertThat(cdmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameFRIsRequired() throws Exception {
        int databaseSizeBeforeTest = cdmRepository.findAll().size();
        // set the field null
        cdm.setNameFR(null);

        // Create the Cdm, which fails.

        restCdmMockMvc.perform(post("/api/cdms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cdm)))
            .andExpect(status().isBadRequest());

        List<Cdm> cdmList = cdmRepository.findAll();
        assertThat(cdmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCommentFRIsRequired() throws Exception {
        int databaseSizeBeforeTest = cdmRepository.findAll().size();
        // set the field null
        cdm.setCommentFR(null);

        // Create the Cdm, which fails.

        restCdmMockMvc.perform(post("/api/cdms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cdm)))
            .andExpect(status().isBadRequest());

        List<Cdm> cdmList = cdmRepository.findAll();
        assertThat(cdmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocNameFRIsRequired() throws Exception {
        int databaseSizeBeforeTest = cdmRepository.findAll().size();
        // set the field null
        cdm.setDocNameFR(null);

        // Create the Cdm, which fails.

        restCdmMockMvc.perform(post("/api/cdms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cdm)))
            .andExpect(status().isBadRequest());

        List<Cdm> cdmList = cdmRepository.findAll();
        assertThat(cdmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBusMessageIsRequired() throws Exception {
        int databaseSizeBeforeTest = cdmRepository.findAll().size();
        // set the field null
        cdm.setBusMessage(null);

        // Create the Cdm, which fails.

        restCdmMockMvc.perform(post("/api/cdms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cdm)))
            .andExpect(status().isBadRequest());

        List<Cdm> cdmList = cdmRepository.findAll();
        assertThat(cdmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBusWordIsRequired() throws Exception {
        int databaseSizeBeforeTest = cdmRepository.findAll().size();
        // set the field null
        cdm.setBusWord(null);

        // Create the Cdm, which fails.

        restCdmMockMvc.perform(post("/api/cdms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cdm)))
            .andExpect(status().isBadRequest());

        List<Cdm> cdmList = cdmRepository.findAll();
        assertThat(cdmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMnemonicFRIsRequired() throws Exception {
        int databaseSizeBeforeTest = cdmRepository.findAll().size();
        // set the field null
        cdm.setMnemonicFR(null);

        // Create the Cdm, which fails.

        restCdmMockMvc.perform(post("/api/cdms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cdm)))
            .andExpect(status().isBadRequest());

        List<Cdm> cdmList = cdmRepository.findAll();
        assertThat(cdmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLinearIsRequired() throws Exception {
        int databaseSizeBeforeTest = cdmRepository.findAll().size();
        // set the field null
        cdm.setLinear(null);

        // Create the Cdm, which fails.

        restCdmMockMvc.perform(post("/api/cdms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cdm)))
            .andExpect(status().isBadRequest());

        List<Cdm> cdmList = cdmRepository.findAll();
        assertThat(cdmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFuncIsRequired() throws Exception {
        int databaseSizeBeforeTest = cdmRepository.findAll().size();
        // set the field null
        cdm.setFunc(null);

        // Create the Cdm, which fails.

        restCdmMockMvc.perform(post("/api/cdms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cdm)))
            .andExpect(status().isBadRequest());

        List<Cdm> cdmList = cdmRepository.findAll();
        assertThat(cdmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCdms() throws Exception {
        // Initialize the database
        cdmRepository.saveAndFlush(cdm);

        // Get all the cdmList
        restCdmMockMvc.perform(get("/api/cdms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cdm.getId().intValue())))
            .andExpect(jsonPath("$.[*].index").value(hasItem(DEFAULT_INDEX)))
            .andExpect(jsonPath("$.[*].nameFR").value(hasItem(DEFAULT_NAME_FR)))
            .andExpect(jsonPath("$.[*].nameGB").value(hasItem(DEFAULT_NAME_GB)))
            .andExpect(jsonPath("$.[*].commentFR").value(hasItem(DEFAULT_COMMENT_FR)))
            .andExpect(jsonPath("$.[*].commentGB").value(hasItem(DEFAULT_COMMENT_GB)))
            .andExpect(jsonPath("$.[*].docNameFR").value(hasItem(DEFAULT_DOC_NAME_FR)))
            .andExpect(jsonPath("$.[*].docNameGB").value(hasItem(DEFAULT_DOC_NAME_GB)))
            .andExpect(jsonPath("$.[*].busMessage").value(hasItem(DEFAULT_BUS_MESSAGE)))
            .andExpect(jsonPath("$.[*].busWord").value(hasItem(DEFAULT_BUS_WORD)))
            .andExpect(jsonPath("$.[*].mnemonicFR").value(hasItem(DEFAULT_MNEMONIC_FR)))
            .andExpect(jsonPath("$.[*].offset").value(hasItem(DEFAULT_OFFSET)))
            .andExpect(jsonPath("$.[*].coding").value(hasItem(DEFAULT_CODING)))
            .andExpect(jsonPath("$.[*].unitMsg").value(hasItem(DEFAULT_UNIT_MSG)))
            .andExpect(jsonPath("$.[*].minMsg").value(hasItem(DEFAULT_MIN_MSG)))
            .andExpect(jsonPath("$.[*].maxMsg").value(hasItem(DEFAULT_MAX_MSG)))
            .andExpect(jsonPath("$.[*].nature").value(hasItem(DEFAULT_NATURE.toString())))
            .andExpect(jsonPath("$.[*].sign").value(hasItem(DEFAULT_SIGN.booleanValue())))
            .andExpect(jsonPath("$.[*].cadrageVTL").value(hasItem(DEFAULT_CADRAGE_VTL)))
            .andExpect(jsonPath("$.[*].minValueVTL").value(hasItem(DEFAULT_MIN_VALUE_VTL)))
            .andExpect(jsonPath("$.[*].maxValueVTL").value(hasItem(DEFAULT_MAX_VALUE_VTL)))
            .andExpect(jsonPath("$.[*].minByteVTL").value(hasItem(DEFAULT_MIN_BYTE_VTL)))
            .andExpect(jsonPath("$.[*].maxByteVTL").value(hasItem(DEFAULT_MAX_BYTE_VTL)))
            .andExpect(jsonPath("$.[*].unitVTL").value(hasItem(DEFAULT_UNIT_VTL)))
            .andExpect(jsonPath("$.[*].kind").value(hasItem(DEFAULT_KIND.toString())))
            .andExpect(jsonPath("$.[*].linear").value(hasItem(DEFAULT_LINEAR.booleanValue())))
            .andExpect(jsonPath("$.[*].func").value(hasItem(DEFAULT_FUNC)));
    }
    
    @Test
    @Transactional
    public void getCdm() throws Exception {
        // Initialize the database
        cdmRepository.saveAndFlush(cdm);

        // Get the cdm
        restCdmMockMvc.perform(get("/api/cdms/{id}", cdm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cdm.getId().intValue()))
            .andExpect(jsonPath("$.index").value(DEFAULT_INDEX))
            .andExpect(jsonPath("$.nameFR").value(DEFAULT_NAME_FR))
            .andExpect(jsonPath("$.nameGB").value(DEFAULT_NAME_GB))
            .andExpect(jsonPath("$.commentFR").value(DEFAULT_COMMENT_FR))
            .andExpect(jsonPath("$.commentGB").value(DEFAULT_COMMENT_GB))
            .andExpect(jsonPath("$.docNameFR").value(DEFAULT_DOC_NAME_FR))
            .andExpect(jsonPath("$.docNameGB").value(DEFAULT_DOC_NAME_GB))
            .andExpect(jsonPath("$.busMessage").value(DEFAULT_BUS_MESSAGE))
            .andExpect(jsonPath("$.busWord").value(DEFAULT_BUS_WORD))
            .andExpect(jsonPath("$.mnemonicFR").value(DEFAULT_MNEMONIC_FR))
            .andExpect(jsonPath("$.offset").value(DEFAULT_OFFSET))
            .andExpect(jsonPath("$.coding").value(DEFAULT_CODING))
            .andExpect(jsonPath("$.unitMsg").value(DEFAULT_UNIT_MSG))
            .andExpect(jsonPath("$.minMsg").value(DEFAULT_MIN_MSG))
            .andExpect(jsonPath("$.maxMsg").value(DEFAULT_MAX_MSG))
            .andExpect(jsonPath("$.nature").value(DEFAULT_NATURE.toString()))
            .andExpect(jsonPath("$.sign").value(DEFAULT_SIGN.booleanValue()))
            .andExpect(jsonPath("$.cadrageVTL").value(DEFAULT_CADRAGE_VTL))
            .andExpect(jsonPath("$.minValueVTL").value(DEFAULT_MIN_VALUE_VTL))
            .andExpect(jsonPath("$.maxValueVTL").value(DEFAULT_MAX_VALUE_VTL))
            .andExpect(jsonPath("$.minByteVTL").value(DEFAULT_MIN_BYTE_VTL))
            .andExpect(jsonPath("$.maxByteVTL").value(DEFAULT_MAX_BYTE_VTL))
            .andExpect(jsonPath("$.unitVTL").value(DEFAULT_UNIT_VTL))
            .andExpect(jsonPath("$.kind").value(DEFAULT_KIND.toString()))
            .andExpect(jsonPath("$.linear").value(DEFAULT_LINEAR.booleanValue()))
            .andExpect(jsonPath("$.func").value(DEFAULT_FUNC));
    }

    @Test
    @Transactional
    public void getNonExistingCdm() throws Exception {
        // Get the cdm
        restCdmMockMvc.perform(get("/api/cdms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCdm() throws Exception {
        // Initialize the database
        cdmRepository.saveAndFlush(cdm);

        int databaseSizeBeforeUpdate = cdmRepository.findAll().size();

        // Update the cdm
        Cdm updatedCdm = cdmRepository.findById(cdm.getId()).get();
        // Disconnect from session so that the updates on updatedCdm are not directly saved in db
        em.detach(updatedCdm);
        updatedCdm
            .index(UPDATED_INDEX)
            .nameFR(UPDATED_NAME_FR)
            .nameGB(UPDATED_NAME_GB)
            .commentFR(UPDATED_COMMENT_FR)
            .commentGB(UPDATED_COMMENT_GB)
            .docNameFR(UPDATED_DOC_NAME_FR)
            .docNameGB(UPDATED_DOC_NAME_GB)
            .busMessage(UPDATED_BUS_MESSAGE)
            .busWord(UPDATED_BUS_WORD)
            .mnemonicFR(UPDATED_MNEMONIC_FR)
            .offset(UPDATED_OFFSET)
            .coding(UPDATED_CODING)
            .unitMsg(UPDATED_UNIT_MSG)
            .minMsg(UPDATED_MIN_MSG)
            .maxMsg(UPDATED_MAX_MSG)
            .nature(UPDATED_NATURE)
            .sign(UPDATED_SIGN)
            .cadrageVTL(UPDATED_CADRAGE_VTL)
            .minValueVTL(UPDATED_MIN_VALUE_VTL)
            .maxValueVTL(UPDATED_MAX_VALUE_VTL)
            .minByteVTL(UPDATED_MIN_BYTE_VTL)
            .maxByteVTL(UPDATED_MAX_BYTE_VTL)
            .unitVTL(UPDATED_UNIT_VTL)
            .kind(UPDATED_KIND)
            .linear(UPDATED_LINEAR)
            .func(UPDATED_FUNC);

        restCdmMockMvc.perform(put("/api/cdms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCdm)))
            .andExpect(status().isOk());

        // Validate the Cdm in the database
        List<Cdm> cdmList = cdmRepository.findAll();
        assertThat(cdmList).hasSize(databaseSizeBeforeUpdate);
        Cdm testCdm = cdmList.get(cdmList.size() - 1);
        assertThat(testCdm.getIndex()).isEqualTo(UPDATED_INDEX);
        assertThat(testCdm.getNameFR()).isEqualTo(UPDATED_NAME_FR);
        assertThat(testCdm.getNameGB()).isEqualTo(UPDATED_NAME_GB);
        assertThat(testCdm.getCommentFR()).isEqualTo(UPDATED_COMMENT_FR);
        assertThat(testCdm.getCommentGB()).isEqualTo(UPDATED_COMMENT_GB);
        assertThat(testCdm.getDocNameFR()).isEqualTo(UPDATED_DOC_NAME_FR);
        assertThat(testCdm.getDocNameGB()).isEqualTo(UPDATED_DOC_NAME_GB);
        assertThat(testCdm.getBusMessage()).isEqualTo(UPDATED_BUS_MESSAGE);
        assertThat(testCdm.getBusWord()).isEqualTo(UPDATED_BUS_WORD);
        assertThat(testCdm.getMnemonicFR()).isEqualTo(UPDATED_MNEMONIC_FR);
        assertThat(testCdm.getOffset()).isEqualTo(UPDATED_OFFSET);
        assertThat(testCdm.getCoding()).isEqualTo(UPDATED_CODING);
        assertThat(testCdm.getUnitMsg()).isEqualTo(UPDATED_UNIT_MSG);
        assertThat(testCdm.getMinMsg()).isEqualTo(UPDATED_MIN_MSG);
        assertThat(testCdm.getMaxMsg()).isEqualTo(UPDATED_MAX_MSG);
        assertThat(testCdm.getNature()).isEqualTo(UPDATED_NATURE);
        assertThat(testCdm.isSign()).isEqualTo(UPDATED_SIGN);
        assertThat(testCdm.getCadrageVTL()).isEqualTo(UPDATED_CADRAGE_VTL);
        assertThat(testCdm.getMinValueVTL()).isEqualTo(UPDATED_MIN_VALUE_VTL);
        assertThat(testCdm.getMaxValueVTL()).isEqualTo(UPDATED_MAX_VALUE_VTL);
        assertThat(testCdm.getMinByteVTL()).isEqualTo(UPDATED_MIN_BYTE_VTL);
        assertThat(testCdm.getMaxByteVTL()).isEqualTo(UPDATED_MAX_BYTE_VTL);
        assertThat(testCdm.getUnitVTL()).isEqualTo(UPDATED_UNIT_VTL);
        assertThat(testCdm.getKind()).isEqualTo(UPDATED_KIND);
        assertThat(testCdm.isLinear()).isEqualTo(UPDATED_LINEAR);
        assertThat(testCdm.getFunc()).isEqualTo(UPDATED_FUNC);
    }

    @Test
    @Transactional
    public void updateNonExistingCdm() throws Exception {
        int databaseSizeBeforeUpdate = cdmRepository.findAll().size();

        // Create the Cdm

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCdmMockMvc.perform(put("/api/cdms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cdm)))
            .andExpect(status().isBadRequest());

        // Validate the Cdm in the database
        List<Cdm> cdmList = cdmRepository.findAll();
        assertThat(cdmList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCdm() throws Exception {
        // Initialize the database
        cdmRepository.saveAndFlush(cdm);

        int databaseSizeBeforeDelete = cdmRepository.findAll().size();

        // Delete the cdm
        restCdmMockMvc.perform(delete("/api/cdms/{id}", cdm.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cdm> cdmList = cdmRepository.findAll();
        assertThat(cdmList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cdm.class);
        Cdm cdm1 = new Cdm();
        cdm1.setId(1L);
        Cdm cdm2 = new Cdm();
        cdm2.setId(cdm1.getId());
        assertThat(cdm1).isEqualTo(cdm2);
        cdm2.setId(2L);
        assertThat(cdm1).isNotEqualTo(cdm2);
        cdm1.setId(null);
        assertThat(cdm1).isNotEqualTo(cdm2);
    }
}
