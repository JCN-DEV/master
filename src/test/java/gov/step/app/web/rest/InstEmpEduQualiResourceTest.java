package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.InstEmpEduQuali;
import gov.step.app.repository.InstEmpEduQualiRepository;
import gov.step.app.repository.search.InstEmpEduQualiSearchRepository;

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
 * Test class for the InstEmpEduQualiResource REST controller.
 *
 * @see InstEmpEduQualiResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstEmpEduQualiResourceTest {

    private static final String DEFAULT_CERTIFICATE_NAME = "AAAAA";
    private static final String UPDATED_CERTIFICATE_NAME = "BBBBB";
    private static final String DEFAULT_BOARD = "AAAAA";
    private static final String UPDATED_BOARD = "BBBBB";
    private static final String DEFAULT_SESSION = "AAAAA";
    private static final String UPDATED_SESSION = "BBBBB";
    private static final String DEFAULT_SEMESTER = "AAAAA";
    private static final String UPDATED_SEMESTER = "BBBBB";
    private static final String DEFAULT_ROLL_NO = "AAAAA";
    private static final String UPDATED_ROLL_NO = "BBBBB";

    private static final Integer DEFAULT_PASSING_YEAR = 1;
    private static final Integer UPDATED_PASSING_YEAR = 2;
    private static final String DEFAULT_CGPA = "AAAAA";
    private static final String UPDATED_CGPA = "BBBBB";

    private static final byte[] DEFAULT_CERTIFICATE_COPY = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CERTIFICATE_COPY = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_CERTIFICATE_COPY_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CERTIFICATE_COPY_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Inject
    private InstEmpEduQualiRepository instEmpEduQualiRepository;

    @Inject
    private InstEmpEduQualiSearchRepository instEmpEduQualiSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstEmpEduQualiMockMvc;

    private InstEmpEduQuali instEmpEduQuali;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstEmpEduQualiResource instEmpEduQualiResource = new InstEmpEduQualiResource();
        ReflectionTestUtils.setField(instEmpEduQualiResource, "instEmpEduQualiRepository", instEmpEduQualiRepository);
        ReflectionTestUtils.setField(instEmpEduQualiResource, "instEmpEduQualiSearchRepository", instEmpEduQualiSearchRepository);
        this.restInstEmpEduQualiMockMvc = MockMvcBuilders.standaloneSetup(instEmpEduQualiResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instEmpEduQuali = new InstEmpEduQuali();
        instEmpEduQuali.setCertificateName(DEFAULT_CERTIFICATE_NAME);
        instEmpEduQuali.setBoard(DEFAULT_BOARD);
        instEmpEduQuali.setSession(DEFAULT_SESSION);
        instEmpEduQuali.setSemester(DEFAULT_SEMESTER);
        instEmpEduQuali.setRollNo(DEFAULT_ROLL_NO);
        instEmpEduQuali.setPassingYear(DEFAULT_PASSING_YEAR);
        instEmpEduQuali.setCgpa(DEFAULT_CGPA);
        instEmpEduQuali.setCertificateCopy(DEFAULT_CERTIFICATE_COPY);
        instEmpEduQuali.setCertificateCopyContentType(DEFAULT_CERTIFICATE_COPY_CONTENT_TYPE);
        instEmpEduQuali.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createInstEmpEduQuali() throws Exception {
        int databaseSizeBeforeCreate = instEmpEduQualiRepository.findAll().size();

        // Create the InstEmpEduQuali

        restInstEmpEduQualiMockMvc.perform(post("/api/instEmpEduQualis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmpEduQuali)))
                .andExpect(status().isCreated());

        // Validate the InstEmpEduQuali in the database
        List<InstEmpEduQuali> instEmpEduQualis = instEmpEduQualiRepository.findAll();
        assertThat(instEmpEduQualis).hasSize(databaseSizeBeforeCreate + 1);
        InstEmpEduQuali testInstEmpEduQuali = instEmpEduQualis.get(instEmpEduQualis.size() - 1);
        assertThat(testInstEmpEduQuali.getCertificateName()).isEqualTo(DEFAULT_CERTIFICATE_NAME);
        assertThat(testInstEmpEduQuali.getBoard()).isEqualTo(DEFAULT_BOARD);
        assertThat(testInstEmpEduQuali.getSession()).isEqualTo(DEFAULT_SESSION);
        assertThat(testInstEmpEduQuali.getSemester()).isEqualTo(DEFAULT_SEMESTER);
        assertThat(testInstEmpEduQuali.getRollNo()).isEqualTo(DEFAULT_ROLL_NO);
        assertThat(testInstEmpEduQuali.getPassingYear()).isEqualTo(DEFAULT_PASSING_YEAR);
        assertThat(testInstEmpEduQuali.getCgpa()).isEqualTo(DEFAULT_CGPA);
        assertThat(testInstEmpEduQuali.getCertificateCopy()).isEqualTo(DEFAULT_CERTIFICATE_COPY);
        assertThat(testInstEmpEduQuali.getCertificateCopyContentType()).isEqualTo(DEFAULT_CERTIFICATE_COPY_CONTENT_TYPE);
        assertThat(testInstEmpEduQuali.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllInstEmpEduQualis() throws Exception {
        // Initialize the database
        instEmpEduQualiRepository.saveAndFlush(instEmpEduQuali);

        // Get all the instEmpEduQualis
        restInstEmpEduQualiMockMvc.perform(get("/api/instEmpEduQualis"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instEmpEduQuali.getId().intValue())))
                .andExpect(jsonPath("$.[*].certificateName").value(hasItem(DEFAULT_CERTIFICATE_NAME.toString())))
                .andExpect(jsonPath("$.[*].board").value(hasItem(DEFAULT_BOARD.toString())))
                .andExpect(jsonPath("$.[*].session").value(hasItem(DEFAULT_SESSION.toString())))
                .andExpect(jsonPath("$.[*].semester").value(hasItem(DEFAULT_SEMESTER.toString())))
                .andExpect(jsonPath("$.[*].rollNo").value(hasItem(DEFAULT_ROLL_NO.toString())))
                .andExpect(jsonPath("$.[*].passingYear").value(hasItem(DEFAULT_PASSING_YEAR)))
                .andExpect(jsonPath("$.[*].cgpa").value(hasItem(DEFAULT_CGPA.toString())))
                .andExpect(jsonPath("$.[*].certificateCopyContentType").value(hasItem(DEFAULT_CERTIFICATE_COPY_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].certificateCopy").value(hasItem(Base64Utils.encodeToString(DEFAULT_CERTIFICATE_COPY))))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getInstEmpEduQuali() throws Exception {
        // Initialize the database
        instEmpEduQualiRepository.saveAndFlush(instEmpEduQuali);

        // Get the instEmpEduQuali
        restInstEmpEduQualiMockMvc.perform(get("/api/instEmpEduQualis/{id}", instEmpEduQuali.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instEmpEduQuali.getId().intValue()))
            .andExpect(jsonPath("$.certificateName").value(DEFAULT_CERTIFICATE_NAME.toString()))
            .andExpect(jsonPath("$.board").value(DEFAULT_BOARD.toString()))
            .andExpect(jsonPath("$.session").value(DEFAULT_SESSION.toString()))
            .andExpect(jsonPath("$.semester").value(DEFAULT_SEMESTER.toString()))
            .andExpect(jsonPath("$.rollNo").value(DEFAULT_ROLL_NO.toString()))
            .andExpect(jsonPath("$.passingYear").value(DEFAULT_PASSING_YEAR))
            .andExpect(jsonPath("$.cgpa").value(DEFAULT_CGPA.toString()))
            .andExpect(jsonPath("$.certificateCopyContentType").value(DEFAULT_CERTIFICATE_COPY_CONTENT_TYPE))
            .andExpect(jsonPath("$.certificateCopy").value(Base64Utils.encodeToString(DEFAULT_CERTIFICATE_COPY)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingInstEmpEduQuali() throws Exception {
        // Get the instEmpEduQuali
        restInstEmpEduQualiMockMvc.perform(get("/api/instEmpEduQualis/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstEmpEduQuali() throws Exception {
        // Initialize the database
        instEmpEduQualiRepository.saveAndFlush(instEmpEduQuali);

		int databaseSizeBeforeUpdate = instEmpEduQualiRepository.findAll().size();

        // Update the instEmpEduQuali
        instEmpEduQuali.setCertificateName(UPDATED_CERTIFICATE_NAME);
        instEmpEduQuali.setBoard(UPDATED_BOARD);
        instEmpEduQuali.setSession(UPDATED_SESSION);
        instEmpEduQuali.setSemester(UPDATED_SEMESTER);
        instEmpEduQuali.setRollNo(UPDATED_ROLL_NO);
        instEmpEduQuali.setPassingYear(UPDATED_PASSING_YEAR);
        instEmpEduQuali.setCgpa(UPDATED_CGPA);
        instEmpEduQuali.setCertificateCopy(UPDATED_CERTIFICATE_COPY);
        instEmpEduQuali.setCertificateCopyContentType(UPDATED_CERTIFICATE_COPY_CONTENT_TYPE);
        instEmpEduQuali.setStatus(UPDATED_STATUS);

        restInstEmpEduQualiMockMvc.perform(put("/api/instEmpEduQualis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instEmpEduQuali)))
                .andExpect(status().isOk());

        // Validate the InstEmpEduQuali in the database
        List<InstEmpEduQuali> instEmpEduQualis = instEmpEduQualiRepository.findAll();
        assertThat(instEmpEduQualis).hasSize(databaseSizeBeforeUpdate);
        InstEmpEduQuali testInstEmpEduQuali = instEmpEduQualis.get(instEmpEduQualis.size() - 1);
        assertThat(testInstEmpEduQuali.getCertificateName()).isEqualTo(UPDATED_CERTIFICATE_NAME);
        assertThat(testInstEmpEduQuali.getBoard()).isEqualTo(UPDATED_BOARD);
        assertThat(testInstEmpEduQuali.getSession()).isEqualTo(UPDATED_SESSION);
        assertThat(testInstEmpEduQuali.getSemester()).isEqualTo(UPDATED_SEMESTER);
        assertThat(testInstEmpEduQuali.getRollNo()).isEqualTo(UPDATED_ROLL_NO);
        assertThat(testInstEmpEduQuali.getPassingYear()).isEqualTo(UPDATED_PASSING_YEAR);
        assertThat(testInstEmpEduQuali.getCgpa()).isEqualTo(UPDATED_CGPA);
        assertThat(testInstEmpEduQuali.getCertificateCopy()).isEqualTo(UPDATED_CERTIFICATE_COPY);
        assertThat(testInstEmpEduQuali.getCertificateCopyContentType()).isEqualTo(UPDATED_CERTIFICATE_COPY_CONTENT_TYPE);
        assertThat(testInstEmpEduQuali.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteInstEmpEduQuali() throws Exception {
        // Initialize the database
        instEmpEduQualiRepository.saveAndFlush(instEmpEduQuali);

		int databaseSizeBeforeDelete = instEmpEduQualiRepository.findAll().size();

        // Get the instEmpEduQuali
        restInstEmpEduQualiMockMvc.perform(delete("/api/instEmpEduQualis/{id}", instEmpEduQuali.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InstEmpEduQuali> instEmpEduQualis = instEmpEduQualiRepository.findAll();
        assertThat(instEmpEduQualis).hasSize(databaseSizeBeforeDelete - 1);
    }
}
