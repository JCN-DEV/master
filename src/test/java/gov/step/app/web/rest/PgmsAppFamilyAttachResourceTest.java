package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.PgmsAppFamilyAttach;
import gov.step.app.repository.PgmsAppFamilyAttachRepository;
import gov.step.app.repository.search.PgmsAppFamilyAttachSearchRepository;

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
 * Test class for the PgmsAppFamilyAttachResource REST controller.
 *
 * @see PgmsAppFamilyAttachResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PgmsAppFamilyAttachResourceTest {


    private static final Long DEFAULT_APP_FAMILY_PEN_ID = 1L;
    private static final Long UPDATED_APP_FAMILY_PEN_ID = 2L;

    private static final byte[] DEFAULT_ATTACHMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHMENT = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ATTACHMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHMENT_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_ATTACH_DOC_NAME = "AAAAA";
    private static final String UPDATED_ATTACH_DOC_NAME = "BBBBB";
    private static final String DEFAULT_ATTACH_DOC_TYPE = "AAAAA";
    private static final String UPDATED_ATTACH_DOC_TYPE = "BBBBB";

    @Inject
    private PgmsAppFamilyAttachRepository pgmsAppFamilyAttachRepository;

    @Inject
    private PgmsAppFamilyAttachSearchRepository pgmsAppFamilyAttachSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPgmsAppFamilyAttachMockMvc;

    private PgmsAppFamilyAttach pgmsAppFamilyAttach;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PgmsAppFamilyAttachResource pgmsAppFamilyAttachResource = new PgmsAppFamilyAttachResource();
        ReflectionTestUtils.setField(pgmsAppFamilyAttachResource, "pgmsAppFamilyAttachRepository", pgmsAppFamilyAttachRepository);
        ReflectionTestUtils.setField(pgmsAppFamilyAttachResource, "pgmsAppFamilyAttachSearchRepository", pgmsAppFamilyAttachSearchRepository);
        this.restPgmsAppFamilyAttachMockMvc = MockMvcBuilders.standaloneSetup(pgmsAppFamilyAttachResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pgmsAppFamilyAttach = new PgmsAppFamilyAttach();
        pgmsAppFamilyAttach.setAppFamilyPenId(DEFAULT_APP_FAMILY_PEN_ID);
        pgmsAppFamilyAttach.setAttachment(DEFAULT_ATTACHMENT);
        pgmsAppFamilyAttach.setAttachmentContentType(DEFAULT_ATTACHMENT_CONTENT_TYPE);
        pgmsAppFamilyAttach.setAttachDocName(DEFAULT_ATTACH_DOC_NAME);
    }

    @Test
    @Transactional
    public void createPgmsAppFamilyAttach() throws Exception {
        int databaseSizeBeforeCreate = pgmsAppFamilyAttachRepository.findAll().size();

        // Create the PgmsAppFamilyAttach

        restPgmsAppFamilyAttachMockMvc.perform(post("/api/pgmsAppFamilyAttachs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppFamilyAttach)))
                .andExpect(status().isCreated());

        // Validate the PgmsAppFamilyAttach in the database
        List<PgmsAppFamilyAttach> pgmsAppFamilyAttachs = pgmsAppFamilyAttachRepository.findAll();
        assertThat(pgmsAppFamilyAttachs).hasSize(databaseSizeBeforeCreate + 1);
        PgmsAppFamilyAttach testPgmsAppFamilyAttach = pgmsAppFamilyAttachs.get(pgmsAppFamilyAttachs.size() - 1);
        assertThat(testPgmsAppFamilyAttach.getAppFamilyPenId()).isEqualTo(DEFAULT_APP_FAMILY_PEN_ID);
        assertThat(testPgmsAppFamilyAttach.getAttachment()).isEqualTo(DEFAULT_ATTACHMENT);
        assertThat(testPgmsAppFamilyAttach.getAttachmentContentType()).isEqualTo(DEFAULT_ATTACHMENT_CONTENT_TYPE);
        assertThat(testPgmsAppFamilyAttach.getAttachDocName()).isEqualTo(DEFAULT_ATTACH_DOC_NAME);
    }

    @Test
    @Transactional
    public void checkAppFamilyPenIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppFamilyAttachRepository.findAll().size();
        // set the field null
        pgmsAppFamilyAttach.setAppFamilyPenId(null);

        // Create the PgmsAppFamilyAttach, which fails.

        restPgmsAppFamilyAttachMockMvc.perform(post("/api/pgmsAppFamilyAttachs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppFamilyAttach)))
                .andExpect(status().isBadRequest());

        List<PgmsAppFamilyAttach> pgmsAppFamilyAttachs = pgmsAppFamilyAttachRepository.findAll();
        assertThat(pgmsAppFamilyAttachs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAttachDocNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgmsAppFamilyAttachRepository.findAll().size();
        // set the field null
        pgmsAppFamilyAttach.setAttachDocName(null);

        // Create the PgmsAppFamilyAttach, which fails.

        restPgmsAppFamilyAttachMockMvc.perform(post("/api/pgmsAppFamilyAttachs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppFamilyAttach)))
                .andExpect(status().isBadRequest());

        List<PgmsAppFamilyAttach> pgmsAppFamilyAttachs = pgmsAppFamilyAttachRepository.findAll();
        assertThat(pgmsAppFamilyAttachs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPgmsAppFamilyAttachs() throws Exception {
        // Initialize the database
        pgmsAppFamilyAttachRepository.saveAndFlush(pgmsAppFamilyAttach);

        // Get all the pgmsAppFamilyAttachs
        restPgmsAppFamilyAttachMockMvc.perform(get("/api/pgmsAppFamilyAttachs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pgmsAppFamilyAttach.getId().intValue())))
                .andExpect(jsonPath("$.[*].appFamilyPenId").value(hasItem(DEFAULT_APP_FAMILY_PEN_ID.intValue())))
                .andExpect(jsonPath("$.[*].attachmentContentType").value(hasItem(DEFAULT_ATTACHMENT_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].attachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT))))
                .andExpect(jsonPath("$.[*].attachDocName").value(hasItem(DEFAULT_ATTACH_DOC_NAME.toString())))
                .andExpect(jsonPath("$.[*].attachDocType").value(hasItem(DEFAULT_ATTACH_DOC_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getPgmsAppFamilyAttach() throws Exception {
        // Initialize the database
        pgmsAppFamilyAttachRepository.saveAndFlush(pgmsAppFamilyAttach);

        // Get the pgmsAppFamilyAttach
        restPgmsAppFamilyAttachMockMvc.perform(get("/api/pgmsAppFamilyAttachs/{id}", pgmsAppFamilyAttach.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pgmsAppFamilyAttach.getId().intValue()))
            .andExpect(jsonPath("$.appFamilyPenId").value(DEFAULT_APP_FAMILY_PEN_ID.intValue()))
            .andExpect(jsonPath("$.attachmentContentType").value(DEFAULT_ATTACHMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachment").value(Base64Utils.encodeToString(DEFAULT_ATTACHMENT)))
            .andExpect(jsonPath("$.attachDocName").value(DEFAULT_ATTACH_DOC_NAME.toString()))
            .andExpect(jsonPath("$.attachDocType").value(DEFAULT_ATTACH_DOC_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPgmsAppFamilyAttach() throws Exception {
        // Get the pgmsAppFamilyAttach
        restPgmsAppFamilyAttachMockMvc.perform(get("/api/pgmsAppFamilyAttachs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgmsAppFamilyAttach() throws Exception {
        // Initialize the database
        pgmsAppFamilyAttachRepository.saveAndFlush(pgmsAppFamilyAttach);

		int databaseSizeBeforeUpdate = pgmsAppFamilyAttachRepository.findAll().size();

        // Update the pgmsAppFamilyAttach
        pgmsAppFamilyAttach.setAppFamilyPenId(UPDATED_APP_FAMILY_PEN_ID);
        pgmsAppFamilyAttach.setAttachment(UPDATED_ATTACHMENT);
        pgmsAppFamilyAttach.setAttachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE);
        pgmsAppFamilyAttach.setAttachDocName(UPDATED_ATTACH_DOC_NAME);

        restPgmsAppFamilyAttachMockMvc.perform(put("/api/pgmsAppFamilyAttachs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pgmsAppFamilyAttach)))
                .andExpect(status().isOk());

        // Validate the PgmsAppFamilyAttach in the database
        List<PgmsAppFamilyAttach> pgmsAppFamilyAttachs = pgmsAppFamilyAttachRepository.findAll();
        assertThat(pgmsAppFamilyAttachs).hasSize(databaseSizeBeforeUpdate);
        PgmsAppFamilyAttach testPgmsAppFamilyAttach = pgmsAppFamilyAttachs.get(pgmsAppFamilyAttachs.size() - 1);
        assertThat(testPgmsAppFamilyAttach.getAppFamilyPenId()).isEqualTo(UPDATED_APP_FAMILY_PEN_ID);
        assertThat(testPgmsAppFamilyAttach.getAttachment()).isEqualTo(UPDATED_ATTACHMENT);
        assertThat(testPgmsAppFamilyAttach.getAttachmentContentType()).isEqualTo(UPDATED_ATTACHMENT_CONTENT_TYPE);
        assertThat(testPgmsAppFamilyAttach.getAttachDocName()).isEqualTo(UPDATED_ATTACH_DOC_NAME);
    }

    @Test
    @Transactional
    public void deletePgmsAppFamilyAttach() throws Exception {
        // Initialize the database
        pgmsAppFamilyAttachRepository.saveAndFlush(pgmsAppFamilyAttach);

		int databaseSizeBeforeDelete = pgmsAppFamilyAttachRepository.findAll().size();

        // Get the pgmsAppFamilyAttach
        restPgmsAppFamilyAttachMockMvc.perform(delete("/api/pgmsAppFamilyAttachs/{id}", pgmsAppFamilyAttach.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PgmsAppFamilyAttach> pgmsAppFamilyAttachs = pgmsAppFamilyAttachRepository.findAll();
        assertThat(pgmsAppFamilyAttachs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
