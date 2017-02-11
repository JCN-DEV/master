package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.EduBoard;
import gov.step.app.repository.EduBoardRepository;
import gov.step.app.repository.search.EduBoardSearchRepository;

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

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the EduBoardResource REST controller.
 *
 * @see EduBoardResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EduBoardResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private EduBoardRepository eduBoardRepository;

    @Inject
    private EduBoardSearchRepository eduBoardSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEduBoardMockMvc;

    private EduBoard eduBoard;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EduBoardResource eduBoardResource = new EduBoardResource();
        ReflectionTestUtils.setField(eduBoardResource, "eduBoardRepository", eduBoardRepository);
        ReflectionTestUtils.setField(eduBoardResource, "eduBoardSearchRepository", eduBoardSearchRepository);
        this.restEduBoardMockMvc = MockMvcBuilders.standaloneSetup(eduBoardResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        eduBoard = new EduBoard();
        eduBoard.setName(DEFAULT_NAME);
        eduBoard.setDescription(DEFAULT_DESCRIPTION);
        eduBoard.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createEduBoard() throws Exception {
        int databaseSizeBeforeCreate = eduBoardRepository.findAll().size();

        // Create the EduBoard

        restEduBoardMockMvc.perform(post("/api/eduBoards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eduBoard)))
                .andExpect(status().isCreated());

        // Validate the EduBoard in the database
        List<EduBoard> eduBoards = eduBoardRepository.findAll();
        assertThat(eduBoards).hasSize(databaseSizeBeforeCreate + 1);
        EduBoard testEduBoard = eduBoards.get(eduBoards.size() - 1);
        assertThat(testEduBoard.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEduBoard.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEduBoard.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = eduBoardRepository.findAll().size();
        // set the field null
        eduBoard.setName(null);

        // Create the EduBoard, which fails.

        restEduBoardMockMvc.perform(post("/api/eduBoards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eduBoard)))
                .andExpect(status().isBadRequest());

        List<EduBoard> eduBoards = eduBoardRepository.findAll();
        assertThat(eduBoards).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEduBoards() throws Exception {
        // Initialize the database
        eduBoardRepository.saveAndFlush(eduBoard);

        // Get all the eduBoards
        restEduBoardMockMvc.perform(get("/api/eduBoards"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(eduBoard.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getEduBoard() throws Exception {
        // Initialize the database
        eduBoardRepository.saveAndFlush(eduBoard);

        // Get the eduBoard
        restEduBoardMockMvc.perform(get("/api/eduBoards/{id}", eduBoard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(eduBoard.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEduBoard() throws Exception {
        // Get the eduBoard
        restEduBoardMockMvc.perform(get("/api/eduBoards/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEduBoard() throws Exception {
        // Initialize the database
        eduBoardRepository.saveAndFlush(eduBoard);

		int databaseSizeBeforeUpdate = eduBoardRepository.findAll().size();

        // Update the eduBoard
        eduBoard.setName(UPDATED_NAME);
        eduBoard.setDescription(UPDATED_DESCRIPTION);
        eduBoard.setStatus(UPDATED_STATUS);

        restEduBoardMockMvc.perform(put("/api/eduBoards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eduBoard)))
                .andExpect(status().isOk());

        // Validate the EduBoard in the database
        List<EduBoard> eduBoards = eduBoardRepository.findAll();
        assertThat(eduBoards).hasSize(databaseSizeBeforeUpdate);
        EduBoard testEduBoard = eduBoards.get(eduBoards.size() - 1);
        assertThat(testEduBoard.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEduBoard.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEduBoard.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteEduBoard() throws Exception {
        // Initialize the database
        eduBoardRepository.saveAndFlush(eduBoard);

		int databaseSizeBeforeDelete = eduBoardRepository.findAll().size();

        // Get the eduBoard
        restEduBoardMockMvc.perform(delete("/api/eduBoards/{id}", eduBoard.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<EduBoard> eduBoards = eduBoardRepository.findAll();
        assertThat(eduBoards).hasSize(databaseSizeBeforeDelete - 1);
    }
}
