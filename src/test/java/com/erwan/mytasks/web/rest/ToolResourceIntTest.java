package com.erwan.mytasks.web.rest;

import com.erwan.mytasks.Application;
import com.erwan.mytasks.domain.Tool;
import com.erwan.mytasks.repository.ToolRepository;
import com.erwan.mytasks.web.rest.dto.ToolDTO;
import com.erwan.mytasks.web.rest.mapper.ToolMapper;

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
 * Test class for the ToolResource REST controller.
 *
 * @see ToolResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ToolResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Boolean DEFAULT_TO_BUY = false;
    private static final Boolean UPDATED_TO_BUY = true;

    private static final Float DEFAULT_PRICE = 1F;
    private static final Float UPDATED_PRICE = 2F;

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;
    private static final String DEFAULT_COMMENT = "AAAAA";
    private static final String UPDATED_COMMENT = "BBBBB";

    @Inject
    private ToolRepository toolRepository;

    @Inject
    private ToolMapper toolMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restToolMockMvc;

    private Tool tool;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ToolResource toolResource = new ToolResource();
        ReflectionTestUtils.setField(toolResource, "toolRepository", toolRepository);
        ReflectionTestUtils.setField(toolResource, "toolMapper", toolMapper);
        this.restToolMockMvc = MockMvcBuilders.standaloneSetup(toolResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        tool = new Tool();
        tool.setName(DEFAULT_NAME);
        tool.setToBuy(DEFAULT_TO_BUY);
        tool.setPrice(DEFAULT_PRICE);
        tool.setNumber(DEFAULT_NUMBER);
        tool.setComment(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createTool() throws Exception {
        int databaseSizeBeforeCreate = toolRepository.findAll().size();

        // Create the Tool
        ToolDTO toolDTO = toolMapper.toolToToolDTO(tool);

        restToolMockMvc.perform(post("/api/tools")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(toolDTO)))
                .andExpect(status().isCreated());

        // Validate the Tool in the database
        List<Tool> tools = toolRepository.findAll();
        assertThat(tools).hasSize(databaseSizeBeforeCreate + 1);
        Tool testTool = tools.get(tools.size() - 1);
        assertThat(testTool.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTool.getToBuy()).isEqualTo(DEFAULT_TO_BUY);
        assertThat(testTool.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testTool.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testTool.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void getAllTools() throws Exception {
        // Initialize the database
        toolRepository.saveAndFlush(tool);

        // Get all the tools
        restToolMockMvc.perform(get("/api/tools?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tool.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].toBuy").value(hasItem(DEFAULT_TO_BUY.booleanValue())))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
                .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
                .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getTool() throws Exception {
        // Initialize the database
        toolRepository.saveAndFlush(tool);

        // Get the tool
        restToolMockMvc.perform(get("/api/tools/{id}", tool.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tool.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.toBuy").value(DEFAULT_TO_BUY.booleanValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTool() throws Exception {
        // Get the tool
        restToolMockMvc.perform(get("/api/tools/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTool() throws Exception {
        // Initialize the database
        toolRepository.saveAndFlush(tool);

		int databaseSizeBeforeUpdate = toolRepository.findAll().size();

        // Update the tool
        tool.setName(UPDATED_NAME);
        tool.setToBuy(UPDATED_TO_BUY);
        tool.setPrice(UPDATED_PRICE);
        tool.setNumber(UPDATED_NUMBER);
        tool.setComment(UPDATED_COMMENT);
        ToolDTO toolDTO = toolMapper.toolToToolDTO(tool);

        restToolMockMvc.perform(put("/api/tools")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(toolDTO)))
                .andExpect(status().isOk());

        // Validate the Tool in the database
        List<Tool> tools = toolRepository.findAll();
        assertThat(tools).hasSize(databaseSizeBeforeUpdate);
        Tool testTool = tools.get(tools.size() - 1);
        assertThat(testTool.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTool.getToBuy()).isEqualTo(UPDATED_TO_BUY);
        assertThat(testTool.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testTool.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testTool.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void deleteTool() throws Exception {
        // Initialize the database
        toolRepository.saveAndFlush(tool);

		int databaseSizeBeforeDelete = toolRepository.findAll().size();

        // Get the tool
        restToolMockMvc.perform(delete("/api/tools/{id}", tool.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Tool> tools = toolRepository.findAll();
        assertThat(tools).hasSize(databaseSizeBeforeDelete - 1);
    }
}
