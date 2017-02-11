package gov.step.app.web.rest;

import gov.step.app.Application;
import gov.step.app.domain.BoardName;
import gov.step.app.repository.BoardNameRepository;
import gov.step.app.repository.search.BoardNameSearchRepository;

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
 * Test class for the BoardNameResource REST controller.
 *
 * @see BoardNameResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BoardNameResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private BoardNameRepository boardNameRepository;

    @Inject
    private BoardNameSearchRepository boardNameSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBoardNameMockMvc;

    private BoardName boardName;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BoardNameResource boardNameResource = new BoardNameResource();
        ReflectionTestUtils.setField(boardNameResource, "boardNameRepository", boardNameRepository);
        ReflectionTestUtils.setField(boardNameResource, "boardNameSearchRepository", boardNameSearchRepository);
        this.restBoardNameMockMvc = MockMvcBuilders.standaloneSetup(boardNameResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        boardName = new BoardName();
        boardName.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createBoardName() throws Exception {
        int databaseSizeBeforeCreate = boardNameRepository.findAll().size();

        // Create the BoardName

        restBoardNameMockMvc.perform(post("/api/boardNames")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(boardName)))
                .andExpect(status().isCreated());

        // Validate the BoardName in the database
        List<BoardName> boardNames = boardNameRepository.findAll();
        assertThat(boardNames).hasSize(databaseSizeBeforeCreate + 1);
        BoardName testBoardName = boardNames.get(boardNames.size() - 1);
        assertThat(testBoardName.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllBoardNames() throws Exception {
        // Initialize the database
        boardNameRepository.saveAndFlush(boardName);

        // Get all the boardNames
        restBoardNameMockMvc.perform(get("/api/boardNames"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(boardName.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getBoardName() throws Exception {
        // Initialize the database
        boardNameRepository.saveAndFlush(boardName);

        // Get the boardName
        restBoardNameMockMvc.perform(get("/api/boardNames/{id}", boardName.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(boardName.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBoardName() throws Exception {
        // Get the boardName
        restBoardNameMockMvc.perform(get("/api/boardNames/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBoardName() throws Exception {
        // Initialize the database
        boardNameRepository.saveAndFlush(boardName);

		int databaseSizeBeforeUpdate = boardNameRepository.findAll().size();

        // Update the boardName
        boardName.setName(UPDATED_NAME);

        restBoardNameMockMvc.perform(put("/api/boardNames")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(boardName)))
                .andExpect(status().isOk());

        // Validate the BoardName in the database
        List<BoardName> boardNames = boardNameRepository.findAll();
        assertThat(boardNames).hasSize(databaseSizeBeforeUpdate);
        BoardName testBoardName = boardNames.get(boardNames.size() - 1);
        assertThat(testBoardName.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteBoardName() throws Exception {
        // Initialize the database
        boardNameRepository.saveAndFlush(boardName);

		int databaseSizeBeforeDelete = boardNameRepository.findAll().size();

        // Get the boardName
        restBoardNameMockMvc.perform(delete("/api/boardNames/{id}", boardName.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<BoardName> boardNames = boardNameRepository.findAll();
        assertThat(boardNames).hasSize(databaseSizeBeforeDelete - 1);
    }
}
