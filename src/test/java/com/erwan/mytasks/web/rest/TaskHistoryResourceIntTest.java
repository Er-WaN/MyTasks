package com.erwan.mytasks.web.rest;

import com.erwan.mytasks.Application;
import com.erwan.mytasks.domain.TaskHistory;
import com.erwan.mytasks.repository.TaskHistoryRepository;
import com.erwan.mytasks.web.rest.dto.TaskHistoryDTO;
import com.erwan.mytasks.web.rest.mapper.TaskHistoryMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.erwan.mytasks.domain.enumeration.TaskAction;

/**
 * Test class for the TaskHistoryResource REST controller.
 *
 * @see TaskHistoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TaskHistoryResourceIntTest {



    private static final TaskAction DEFAULT_ACTION = TaskAction.CREATE;
    private static final TaskAction UPDATED_ACTION = TaskAction.EDIT;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_COMMENT = "AAAAA";
    private static final String UPDATED_COMMENT = "BBBBB";

    private static final Integer DEFAULT_SPENT_TIME = 1;
    private static final Integer UPDATED_SPENT_TIME = 2;

    @Inject
    private TaskHistoryRepository taskHistoryRepository;

    @Inject
    private TaskHistoryMapper taskHistoryMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTaskHistoryMockMvc;

    private TaskHistory taskHistory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TaskHistoryResource taskHistoryResource = new TaskHistoryResource();
        ReflectionTestUtils.setField(taskHistoryResource, "taskHistoryRepository", taskHistoryRepository);
        ReflectionTestUtils.setField(taskHistoryResource, "taskHistoryMapper", taskHistoryMapper);
        this.restTaskHistoryMockMvc = MockMvcBuilders.standaloneSetup(taskHistoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        taskHistory = new TaskHistory();
        taskHistory.setAction(DEFAULT_ACTION);
        taskHistory.setDate(DEFAULT_DATE);
        taskHistory.setComment(DEFAULT_COMMENT);
        taskHistory.setSpentTime(DEFAULT_SPENT_TIME);
    }

    @Test
    @Transactional
    public void createTaskHistory() throws Exception {
        int databaseSizeBeforeCreate = taskHistoryRepository.findAll().size();

        // Create the TaskHistory
        TaskHistoryDTO taskHistoryDTO = taskHistoryMapper.taskHistoryToTaskHistoryDTO(taskHistory);

        restTaskHistoryMockMvc.perform(post("/api/taskHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taskHistoryDTO)))
                .andExpect(status().isCreated());

        // Validate the TaskHistory in the database
        List<TaskHistory> taskHistorys = taskHistoryRepository.findAll();
        assertThat(taskHistorys).hasSize(databaseSizeBeforeCreate + 1);
        TaskHistory testTaskHistory = taskHistorys.get(taskHistorys.size() - 1);
        assertThat(testTaskHistory.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testTaskHistory.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTaskHistory.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testTaskHistory.getSpentTime()).isEqualTo(DEFAULT_SPENT_TIME);
    }

    @Test
    @Transactional
    public void getAllTaskHistorys() throws Exception {
        // Initialize the database
        taskHistoryRepository.saveAndFlush(taskHistory);

        // Get all the taskHistorys
        restTaskHistoryMockMvc.perform(get("/api/taskHistorys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(taskHistory.getId().intValue())))
                .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION.toString())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
                .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
                .andExpect(jsonPath("$.[*].spentTime").value(hasItem(DEFAULT_SPENT_TIME)));
    }

    @Test
    @Transactional
    public void getTaskHistory() throws Exception {
        // Initialize the database
        taskHistoryRepository.saveAndFlush(taskHistory);

        // Get the taskHistory
        restTaskHistoryMockMvc.perform(get("/api/taskHistorys/{id}", taskHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(taskHistory.getId().intValue()))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.spentTime").value(DEFAULT_SPENT_TIME));
    }

    @Test
    @Transactional
    public void getNonExistingTaskHistory() throws Exception {
        // Get the taskHistory
        restTaskHistoryMockMvc.perform(get("/api/taskHistorys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaskHistory() throws Exception {
        // Initialize the database
        taskHistoryRepository.saveAndFlush(taskHistory);

		int databaseSizeBeforeUpdate = taskHistoryRepository.findAll().size();

        // Update the taskHistory
        taskHistory.setAction(UPDATED_ACTION);
        taskHistory.setDate(UPDATED_DATE);
        taskHistory.setComment(UPDATED_COMMENT);
        taskHistory.setSpentTime(UPDATED_SPENT_TIME);
        TaskHistoryDTO taskHistoryDTO = taskHistoryMapper.taskHistoryToTaskHistoryDTO(taskHistory);

        restTaskHistoryMockMvc.perform(put("/api/taskHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taskHistoryDTO)))
                .andExpect(status().isOk());

        // Validate the TaskHistory in the database
        List<TaskHistory> taskHistorys = taskHistoryRepository.findAll();
        assertThat(taskHistorys).hasSize(databaseSizeBeforeUpdate);
        TaskHistory testTaskHistory = taskHistorys.get(taskHistorys.size() - 1);
        assertThat(testTaskHistory.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testTaskHistory.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTaskHistory.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testTaskHistory.getSpentTime()).isEqualTo(UPDATED_SPENT_TIME);
    }

    @Test
    @Transactional
    public void deleteTaskHistory() throws Exception {
        // Initialize the database
        taskHistoryRepository.saveAndFlush(taskHistory);

		int databaseSizeBeforeDelete = taskHistoryRepository.findAll().size();

        // Get the taskHistory
        restTaskHistoryMockMvc.perform(delete("/api/taskHistorys/{id}", taskHistory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TaskHistory> taskHistorys = taskHistoryRepository.findAll();
        assertThat(taskHistorys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
