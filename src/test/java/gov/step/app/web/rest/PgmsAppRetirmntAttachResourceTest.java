package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.PgmsAppRetirmntAttach;
import gov.step.app.repository.PgmsAppRetirmntAttachRepository;
import gov.step.app.repository.search.PgmsAppRetirmntAttachSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the PgmsAppRetirmntAttachResource REST controller.
 *
 * @see PgmsAppRetirmntAttachResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PgmsAppRetirmntAttachResourceTest {


    private static final Long DEFAULT_APP_RETIRMNT_PEN_ID = 1L;
    private static final Long UPDATED_APP_RETIRMNT_PEN_ID = 2L;

    private static final byte[] DEFAULT_ATTACHMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHMENT = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ATTACHMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHMENT_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_ATTACH_DOC_NAME = "AAAAA";
    private static final String UPDATED_ATTACH_DOC_NAME = "BBBBB";
    /*private static final String DEFAULT_ATTACH_DOC_TYPE = "AAAAA";
    private static final String UPDATED_ATTACH_DOC_TYPE = "BBBBB";*/

    @Inject
    private PgmsAppRetirmntAttachRepository pgmsAppRetirmntAttachRepository;

    @Inject
    private PgmsAppRetirmntAttachSearchRepository pgmsAppRetirmntAttachSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPgmsAppRetirmntAttachMockMvc;

    private PgmsAppRetirmntAttach pgmsAppRetirmntAttach;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PgmsAppRetirmntAttachResource pgmsAppRetirmntAttachResource = new PgmsAppRetirmntAttachResource();
        ReflectionTestUtils.setField(pgmsAppRetirmntAttachResource, "pgmsAppRetirmntAttachRepository", pgmsAppRetirmntAttachRepository);
        ReflectionTestUtils.setField(pgmsAppRetirmntAttachResource, "pgmsAppRetirmntAttachSearchRepository", pgmsAppRetirmntAttachSearchRepository);
        this.restPgmsAppRetirmntAttachMockMvc = MockMvcBuilders.standaloneSetup(pgmsAppRetirmntAttachResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pgmsAppRetirmntAttach = new PgmsAppRetirmntAttach();
        pgmsAppRetirmntAttach.setAppRetirmntPenId(DEFAULT_APP_RETIRMNT_PEN_ID);
        pgmsAppRetirmntAttach.setAttachment(DEFAULT_ATTACHMENT);
        pgmsAppRetirmntAttach.setAttachmentContentType(DEFAULT_ATTACHMENT_CONTENT_TYPE);
        pgmsAppRetirmntAttach.setAttachDocName(DEFAULT_ATTACH_DOC_NAME);
        //pgmsAppRetirmntAttach.setAttachDocType(DEFAULT_ATTACH_DOC_TYPE);
        /*pgmsAppRetirmntAttach.setAttachDocType(DEFAULT_ATTACH_DOC_TYPE);*/
    }

    @Test
    @Transactional
    public void createPgmsAppRetirmntAttach() throws Exception {
        int databaseSizeBeforeCreate = pgmsAppRetirmntAttachRepository.findAll().size();

        // Create the PgmsAppRetirmntAttach

        restPgmsAppRetirmntAttachMockMvc.perform(post("/api/pgmsAppRetirmntAttachs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppRetirmntAttach)))
                .andExpect(status().isCreated());

        // Validate the PgmsAppRetirmntAttach in the database
        List<PgmsAppRetirmntAttach> pgmsAppRetirmntAttachs = pgmsAppRetirmntAttachRepository.findAll();
        assertThat(pgmsAppRetirmntAttachs).hasSize(databaseSizeBeforeCreate + 1);
        PgmsAppRetirmntAttach testPgmsAppRetirmntAttach = pgmsAppRetirmntAttachs.get(pgmsAppRetirmntAttachs.size() - 1);
        assertThat(testPgmsAppRetirmntAttach.getAppRetirmntPenId()).isEqualTo(DEFAULT_APP_RETIRMNT_PEN_ID);
        assertThat(testPgmsAppRetirmntAttach.getAttachment()).isEqualTo(DEFAULT_ATTACHMENT);
        assertThat(testPgmsAppRetirmntAttach.getAttachmentContentType()).isEqualTo(DEFAULT_ATTACHMENT_CONTENT_TYPE);
        assertThat(testPgmsAppRetirmntAttach.getAttachDocName()).isEqualTo(DEFAULT_ATTACH_DOC_NAME);
        //assertThat(testPgmsAppRetirmntAttach.getAttachDocType()).isEqualTo(DEFAULT_ATTACH_DOC_TYPE);
        /*assertThat(testPgmsAppRetirmntAttach.getAttachDocType()).isEqualTo(DEFAULT_ATTACH_DOC_TYPE);*/
    }

    @Test
    @Transactional
    public void checkAppRetirmntPenIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppRetirmntAttachRepository.findAll().size();
        // set the field null
        pgmsAppRetirmntAttach.setAppRetirmntPenId(null);

        // Create the PgmsAppRetirmntAttach, which fails.

        restPgmsAppRetirmntAttachMockMvc.perform(post("/api/pgmsAppRetirmntAttachs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppRetirmntAttach)))
                .andExpect(status().isBadRequest());

        List<PgmsAppRetirmntAttach> pgmsAppRetirmntAttachs = pgmsAppRetirmntAttachRepository.findAll();
        assertThat(pgmsAppRetirmntAttachs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAttachDocNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppRetirmntAttachRepository.findAll().size();
        // set the field null
        pgmsAppRetirmntAttach.setAttachDocName(null);

        // Create the PgmsAppRetirmntAttach, which fails.

        restPgmsAppRetirmntAttachMockMvc.perform(post("/api/pgmsAppRetirmntAttachs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppRetirmntAttach)))
                .andExpect(status().isBadRequest());

        List<PgmsAppRetirmntAttach> pgmsAppRetirmntAttachs = pgmsAppRetirmntAttachRepository.findAll();
        assertThat(pgmsAppRetirmntAttachs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAttachDocTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppRetirmntAttachRepository.findAll().size();
        // set the field null
       /* pgmsAppRetirmntAttach.setAttachDocType(null);*/
        //pgmsAppRetirmntAttach.setAttachDocType(null);

        // Create the PgmsAppRetirmntAttach, which fails.

        restPgmsAppRetirmntAttachMockMvc.perform(post("/api/pgmsAppRetirmntAttachs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppRetirmntAttach)))
                .andExpect(status().isBadRequest());

        List<PgmsAppRetirmntAttach> pgmsAppRetirmntAttachs = pgmsAppRetirmntAttachRepository.findAll();
        assertThat(pgmsAppRetirmntAttachs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPgmsAppRetirmntAttachs() throws Exception {
        // Initialize the database
        pgmsAppRetirmntAttachRepository.saveAndFlush(pgmsAppRetirmntAttach);

        // Get all the pgmsAppRetirmntAttachs
        restPgmsAppRetirmntAttachMockMvc.perform(get("/api/pgmsAppRetirmntAttachs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pgmsAppRetirmntAttach.getId().intValue())))
                .andExpect(jsonPath("$.[*].appRetirmntPenId").value(hasItem(DEFAULT_APP_RETIRMNT_PEN_ID.intValue())))
                .andExpect(jsonPath("$.[*].attachmentContentType").value(hasItem(DEFAULT_ATTACHMENT_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].attachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT))))
                .andExpect(jsonPath("$.[*].attachDocName").value(hasItem(DEFAULT_ATTACH_DOC_NAME.toString())));
                /*.andExpect(jsonPath("$.[*].attachDocType").value(hasItem(DEFAULT_ATTACH_DOC_TYPE.toString())));*/
    }

    @Test
    @Transactional
    public void getPgmsAppRetirmntAttach() throws Exception {
        // Initialize the database
        pgmsAppRetirmntAttachRepository.saveAndFlush(pgmsAppRetirmntAttach);

        // Get the pgmsAppRetirmntAttach
        restPgmsAppRetirmntAttachMockMvc.perform(get("/api/pgmsAppRetirmntAttachs/{id}", pgmsAppRetirmntAttach.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pgmsAppRetirmntAttach.getId().intValue()))
            .andExpect(jsonPath("$.appRetirmntPenId").value(DEFAULT_APP_RETIRMNT_PEN_ID.intValue()))
            .andExpect(jsonPath("$.attachmentContentType").value(DEFAULT_ATTACHMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachment").value(Base64Utils.encodeToString(DEFAULT_ATTACHMENT)))
            .andExpect(jsonPath("$.attachDocName").value(DEFAULT_ATTACH_DOC_NAME.toString()));
            /*.andExpect(jsonPath("$.attachDocType").value(DEFAULT_ATTACH_DOC_TYPE.toString()));*/
    }

    @Test
    @Transactional
    public void getNonExistingPgmsAppRetirmntAttach() throws Exception {
        // Get the pgmsAppRetirmntAttach
        restPgmsAppRetirmntAttachMockMvc.perform(get("/api/pgmsAppRetirmntAttachs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgmsAppRetirmntAttach() throws Exception {
        // Initialize the database
        pgmsAppRetirmntAttachRepository.saveAndFlush(pgmsAppRetirmntAttach);

		int databaseSizeBeforeUpdate = pgmsAppRetirmntAttachRepository.findAll().size();

        // Update the pgmsAppRetirmntAttach
        pgmsAppRetirmntAttach.setAppRetirmntPenId(UPDATED_APP_RETIRMNT_PEN_ID);
        pgmsAppRetirmntAttach.setAttachment(UPDATED_ATTACHMENT);
        pgmsAppRetirmntAttach.setAttachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE);
        pgmsAppRetirmntAttach.setAttachDocName(UPDATED_ATTACH_DOC_NAME);
        //pgmsAppRetirmntAttach.setAttachDocType(UPDATED_ATTACH_DOC_TYPE);
        /*pgmsAppRetirmntAttach.setAttachDocType(UPDATED_ATTACH_DOC_TYPE);*/

        restPgmsAppRetirmntAttachMockMvc.perform(put("/api/pgmsAppRetirmntAttachs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppRetirmntAttach)))
                .andExpect(status().isOk());

        // Validate the PgmsAppRetirmntAttach in the database
        List<PgmsAppRetirmntAttach> pgmsAppRetirmntAttachs = pgmsAppRetirmntAttachRepository.findAll();
        assertThat(pgmsAppRetirmntAttachs).hasSize(databaseSizeBeforeUpdate);
        PgmsAppRetirmntAttach testPgmsAppRetirmntAttach = pgmsAppRetirmntAttachs.get(pgmsAppRetirmntAttachs.size() - 1);
        assertThat(testPgmsAppRetirmntAttach.getAppRetirmntPenId()).isEqualTo(UPDATED_APP_RETIRMNT_PEN_ID);
        assertThat(testPgmsAppRetirmntAttach.getAttachment()).isEqualTo(UPDATED_ATTACHMENT);
        assertThat(testPgmsAppRetirmntAttach.getAttachmentContentType()).isEqualTo(UPDATED_ATTACHMENT_CONTENT_TYPE);
        assertThat(testPgmsAppRetirmntAttach.getAttachDocName()).isEqualTo(UPDATED_ATTACH_DOC_NAME);
        /*assertThat(testPgmsAppRetirmntAttach.getAttachDocType()).isEqualTo(UPDATED_ATTACH_DOC_TYPE);*/
        //assertThat(testPgmsAppRetirmntAttach.getAttachDocType()).isEqualTo(UPDATED_ATTACH_DOC_TYPE);
    }

    @Test
    @Transactional
    public void deletePgmsAppRetirmntAttach() throws Exception {
        // Initialize the database
        pgmsAppRetirmntAttachRepository.saveAndFlush(pgmsAppRetirmntAttach);

		int databaseSizeBeforeDelete = pgmsAppRetirmntAttachRepository.findAll().size();

        // Get the pgmsAppRetirmntAttach
        restPgmsAppRetirmntAttachMockMvc.perform(delete("/api/pgmsAppRetirmntAttachs/{id}", pgmsAppRetirmntAttach.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PgmsAppRetirmntAttach> pgmsAppRetirmntAttachs = pgmsAppRetirmntAttachRepository.findAll();
        assertThat(pgmsAppRetirmntAttachs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
