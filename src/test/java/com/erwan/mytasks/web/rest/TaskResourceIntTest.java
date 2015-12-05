package com.erwan.mytasks.web.rest;

import com.erwan.mytasks.Application;
import com.erwan.mytasks.domain.Task;
import com.erwan.mytasks.repository.TaskRepository;
import com.erwan.mytasks.web.rest.dto.TaskDTO;
import com.erwan.mytasks.web.rest.mapper.TaskMapper;

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

import com.erwan.mytasks.domain.enumeration.TaskPriority;
import com.erwan.mytasks.domain.enumeration.TaskComplexity;
import com.erwan.mytasks.domain.enumeration.TaskState;

/**
 * Test class for the TaskResource REST controller.
 *
 * @see TaskResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TaskResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_EDIT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_EDIT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FINISH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FINISH_DATE = LocalDate.now(ZoneId.systemDefault());


    private static final TaskPriority DEFAULT_PRIORITY = TaskPriority.LOW;
    private static final TaskPriority UPDATED_PRIORITY = TaskPriority.MEDIUM;


    private static final TaskComplexity DEFAULT_COMPLEXITY = TaskComplexity.EASY;
    private static final TaskComplexity UPDATED_COMPLEXITY = TaskComplexity.MEDIUM;


    private static final TaskState DEFAULT_STATE = TaskState.OPEN;
    private static final TaskState UPDATED_STATE = TaskState.PENDING;

    private static final Integer DEFAULT_ESTIMATED_TIME = 1;
    private static final Integer UPDATED_ESTIMATED_TIME = 2;
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_COMMENT = "AAAAA";
    private static final String UPDATED_COMMENT = "BBBBB";

    @Inject
    private TaskRepository taskRepository;

    @Inject
    private TaskMapper taskMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTaskMockMvc;

    private Task task;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TaskResource taskResource = new TaskResource();
        ReflectionTestUtils.setField(taskResource, "taskRepository", taskRepository);
        ReflectionTestUtils.setField(taskResource, "taskMapper", taskMapper);
        this.restTaskMockMvc = MockMvcBuilders.standaloneSetup(taskResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        task = new Task();
        task.setName(DEFAULT_NAME);
        task.setCreationDate(DEFAULT_CREATION_DATE);
        task.setLastEditDate(DEFAULT_LAST_EDIT_DATE);
        task.setFinishDate(DEFAULT_FINISH_DATE);
        task.setPriority(DEFAULT_PRIORITY);
        task.setComplexity(DEFAULT_COMPLEXITY);
        task.setState(DEFAULT_STATE);
        task.setEstimatedTime(DEFAULT_ESTIMATED_TIME);
        task.setDescription(DEFAULT_DESCRIPTION);
        task.setComment(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createTask() throws Exception {
        int databaseSizeBeforeCreate = taskRepository.findAll().size();

        // Create the Task
        TaskDTO taskDTO = taskMapper.taskToTaskDTO(task);

        restTaskMockMvc.perform(post("/api/tasks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taskDTO)))
                .andExpect(status().isCreated());

        // Validate the Task in the database
        List<Task> tasks = taskRepository.findAll();
        assertThat(tasks).hasSize(databaseSizeBeforeCreate + 1);
        Task testTask = tasks.get(tasks.size() - 1);
        assertThat(testTask.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTask.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testTask.getLastEditDate()).isEqualTo(DEFAULT_LAST_EDIT_DATE);
        assertThat(testTask.getFinishDate()).isEqualTo(DEFAULT_FINISH_DATE);
        assertThat(testTask.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testTask.getComplexity()).isEqualTo(DEFAULT_COMPLEXITY);
        assertThat(testTask.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testTask.getEstimatedTime()).isEqualTo(DEFAULT_ESTIMATED_TIME);
        assertThat(testTask.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTask.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void getAllTasks() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the tasks
        restTaskMockMvc.perform(get("/api/tasks?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(task.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
                .andExpect(jsonPath("$.[*].lastEditDate").value(hasItem(DEFAULT_LAST_EDIT_DATE.toString())))
                .andExpect(jsonPath("$.[*].finishDate").value(hasItem(DEFAULT_FINISH_DATE.toString())))
                .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())))
                .andExpect(jsonPath("$.[*].complexity").value(hasItem(DEFAULT_COMPLEXITY.toString())))
                .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
                .andExpect(jsonPath("$.[*].estimatedTime").value(hasItem(DEFAULT_ESTIMATED_TIME)))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getTask() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get the task
        restTaskMockMvc.perform(get("/api/tasks/{id}", task.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(task.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.lastEditDate").value(DEFAULT_LAST_EDIT_DATE.toString()))
            .andExpect(jsonPath("$.finishDate").value(DEFAULT_FINISH_DATE.toString()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.toString()))
            .andExpect(jsonPath("$.complexity").value(DEFAULT_COMPLEXITY.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.estimatedTime").value(DEFAULT_ESTIMATED_TIME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTask() throws Exception {
        // Get the task
        restTaskMockMvc.perform(get("/api/tasks/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTask() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

		int databaseSizeBeforeUpdate = taskRepository.findAll().size();

        // Update the task
        task.setName(UPDATED_NAME);
        task.setCreationDate(UPDATED_CREATION_DATE);
        task.setLastEditDate(UPDATED_LAST_EDIT_DATE);
        task.setFinishDate(UPDATED_FINISH_DATE);
        task.setPriority(UPDATED_PRIORITY);
        task.setComplexity(UPDATED_COMPLEXITY);
        task.setState(UPDATED_STATE);
        task.setEstimatedTime(UPDATED_ESTIMATED_TIME);
        task.setDescription(UPDATED_DESCRIPTION);
        task.setComment(UPDATED_COMMENT);
        TaskDTO taskDTO = taskMapper.taskToTaskDTO(task);

        restTaskMockMvc.perform(put("/api/tasks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taskDTO)))
                .andExpect(status().isOk());

        // Validate the Task in the database
        List<Task> tasks = taskRepository.findAll();
        assertThat(tasks).hasSize(databaseSizeBeforeUpdate);
        Task testTask = tasks.get(tasks.size() - 1);
        assertThat(testTask.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTask.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testTask.getLastEditDate()).isEqualTo(UPDATED_LAST_EDIT_DATE);
        assertThat(testTask.getFinishDate()).isEqualTo(UPDATED_FINISH_DATE);
        assertThat(testTask.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testTask.getComplexity()).isEqualTo(UPDATED_COMPLEXITY);
        assertThat(testTask.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testTask.getEstimatedTime()).isEqualTo(UPDATED_ESTIMATED_TIME);
        assertThat(testTask.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTask.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void deleteTask() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

		int databaseSizeBeforeDelete = taskRepository.findAll().size();

        // Get the task
        restTaskMockMvc.perform(delete("/api/tasks/{id}", task.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Task> tasks = taskRepository.findAll();
        assertThat(tasks).hasSize(databaseSizeBeforeDelete - 1);
    }
}
