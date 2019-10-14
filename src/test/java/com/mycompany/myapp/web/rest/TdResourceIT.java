package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestmdcApp;
import com.mycompany.myapp.domain.Td;
import com.mycompany.myapp.repository.TdRepository;
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
 * Integration tests for the {@link TdResource} REST controller.
 */
@SpringBootTest(classes = TestmdcApp.class)
public class TdResourceIT {

    private static final String DEFAULT_NAME_FR = "AAAAAAAAAA";
    private static final String UPDATED_NAME_FR = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_GB = "AAAAAAAAAA";
    private static final String UPDATED_NAME_GB = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT_FR = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT_FR = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT_GB = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT_GB = "BBBBBBBBBB";

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    @Autowired
    private TdRepository tdRepository;

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

    private MockMvc restTdMockMvc;

    private Td td;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TdResource tdResource = new TdResource(tdRepository);
        this.restTdMockMvc = MockMvcBuilders.standaloneSetup(tdResource)
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
    public static Td createEntity(EntityManager em) {
        Td td = new Td()
            .nameFR(DEFAULT_NAME_FR)
            .nameGB(DEFAULT_NAME_GB)
            .commentFR(DEFAULT_COMMENT_FR)
            .commentGB(DEFAULT_COMMENT_GB)
            .number(DEFAULT_NUMBER);
        return td;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Td createUpdatedEntity(EntityManager em) {
        Td td = new Td()
            .nameFR(UPDATED_NAME_FR)
            .nameGB(UPDATED_NAME_GB)
            .commentFR(UPDATED_COMMENT_FR)
            .commentGB(UPDATED_COMMENT_GB)
            .number(UPDATED_NUMBER);
        return td;
    }

    @BeforeEach
    public void initTest() {
        td = createEntity(em);
    }

    @Test
    @Transactional
    public void createTd() throws Exception {
        int databaseSizeBeforeCreate = tdRepository.findAll().size();

        // Create the Td
        restTdMockMvc.perform(post("/api/tds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(td)))
            .andExpect(status().isCreated());

        // Validate the Td in the database
        List<Td> tdList = tdRepository.findAll();
        assertThat(tdList).hasSize(databaseSizeBeforeCreate + 1);
        Td testTd = tdList.get(tdList.size() - 1);
        assertThat(testTd.getNameFR()).isEqualTo(DEFAULT_NAME_FR);
        assertThat(testTd.getNameGB()).isEqualTo(DEFAULT_NAME_GB);
        assertThat(testTd.getCommentFR()).isEqualTo(DEFAULT_COMMENT_FR);
        assertThat(testTd.getCommentGB()).isEqualTo(DEFAULT_COMMENT_GB);
        assertThat(testTd.getNumber()).isEqualTo(DEFAULT_NUMBER);
    }

    @Test
    @Transactional
    public void createTdWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tdRepository.findAll().size();

        // Create the Td with an existing ID
        td.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTdMockMvc.perform(post("/api/tds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(td)))
            .andExpect(status().isBadRequest());

        // Validate the Td in the database
        List<Td> tdList = tdRepository.findAll();
        assertThat(tdList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameFRIsRequired() throws Exception {
        int databaseSizeBeforeTest = tdRepository.findAll().size();
        // set the field null
        td.setNameFR(null);

        // Create the Td, which fails.

        restTdMockMvc.perform(post("/api/tds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(td)))
            .andExpect(status().isBadRequest());

        List<Td> tdList = tdRepository.findAll();
        assertThat(tdList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCommentFRIsRequired() throws Exception {
        int databaseSizeBeforeTest = tdRepository.findAll().size();
        // set the field null
        td.setCommentFR(null);

        // Create the Td, which fails.

        restTdMockMvc.perform(post("/api/tds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(td)))
            .andExpect(status().isBadRequest());

        List<Td> tdList = tdRepository.findAll();
        assertThat(tdList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = tdRepository.findAll().size();
        // set the field null
        td.setNumber(null);

        // Create the Td, which fails.

        restTdMockMvc.perform(post("/api/tds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(td)))
            .andExpect(status().isBadRequest());

        List<Td> tdList = tdRepository.findAll();
        assertThat(tdList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTds() throws Exception {
        // Initialize the database
        tdRepository.saveAndFlush(td);

        // Get all the tdList
        restTdMockMvc.perform(get("/api/tds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(td.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameFR").value(hasItem(DEFAULT_NAME_FR)))
            .andExpect(jsonPath("$.[*].nameGB").value(hasItem(DEFAULT_NAME_GB)))
            .andExpect(jsonPath("$.[*].commentFR").value(hasItem(DEFAULT_COMMENT_FR)))
            .andExpect(jsonPath("$.[*].commentGB").value(hasItem(DEFAULT_COMMENT_GB)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getTd() throws Exception {
        // Initialize the database
        tdRepository.saveAndFlush(td);

        // Get the td
        restTdMockMvc.perform(get("/api/tds/{id}", td.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(td.getId().intValue()))
            .andExpect(jsonPath("$.nameFR").value(DEFAULT_NAME_FR))
            .andExpect(jsonPath("$.nameGB").value(DEFAULT_NAME_GB))
            .andExpect(jsonPath("$.commentFR").value(DEFAULT_COMMENT_FR))
            .andExpect(jsonPath("$.commentGB").value(DEFAULT_COMMENT_GB))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER));
    }

    @Test
    @Transactional
    public void getNonExistingTd() throws Exception {
        // Get the td
        restTdMockMvc.perform(get("/api/tds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTd() throws Exception {
        // Initialize the database
        tdRepository.saveAndFlush(td);

        int databaseSizeBeforeUpdate = tdRepository.findAll().size();

        // Update the td
        Td updatedTd = tdRepository.findById(td.getId()).get();
        // Disconnect from session so that the updates on updatedTd are not directly saved in db
        em.detach(updatedTd);
        updatedTd
            .nameFR(UPDATED_NAME_FR)
            .nameGB(UPDATED_NAME_GB)
            .commentFR(UPDATED_COMMENT_FR)
            .commentGB(UPDATED_COMMENT_GB)
            .number(UPDATED_NUMBER);

        restTdMockMvc.perform(put("/api/tds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTd)))
            .andExpect(status().isOk());

        // Validate the Td in the database
        List<Td> tdList = tdRepository.findAll();
        assertThat(tdList).hasSize(databaseSizeBeforeUpdate);
        Td testTd = tdList.get(tdList.size() - 1);
        assertThat(testTd.getNameFR()).isEqualTo(UPDATED_NAME_FR);
        assertThat(testTd.getNameGB()).isEqualTo(UPDATED_NAME_GB);
        assertThat(testTd.getCommentFR()).isEqualTo(UPDATED_COMMENT_FR);
        assertThat(testTd.getCommentGB()).isEqualTo(UPDATED_COMMENT_GB);
        assertThat(testTd.getNumber()).isEqualTo(UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingTd() throws Exception {
        int databaseSizeBeforeUpdate = tdRepository.findAll().size();

        // Create the Td

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTdMockMvc.perform(put("/api/tds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(td)))
            .andExpect(status().isBadRequest());

        // Validate the Td in the database
        List<Td> tdList = tdRepository.findAll();
        assertThat(tdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTd() throws Exception {
        // Initialize the database
        tdRepository.saveAndFlush(td);

        int databaseSizeBeforeDelete = tdRepository.findAll().size();

        // Delete the td
        restTdMockMvc.perform(delete("/api/tds/{id}", td.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Td> tdList = tdRepository.findAll();
        assertThat(tdList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Td.class);
        Td td1 = new Td();
        td1.setId(1L);
        Td td2 = new Td();
        td2.setId(td1.getId());
        assertThat(td1).isEqualTo(td2);
        td2.setId(2L);
        assertThat(td1).isNotEqualTo(td2);
        td1.setId(null);
        assertThat(td1).isNotEqualTo(td2);
    }
}
