package com.erwan.mytasks.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.erwan.mytasks.domain.Tool;
import com.erwan.mytasks.repository.ToolRepository;
import com.erwan.mytasks.web.rest.util.HeaderUtil;
import com.erwan.mytasks.web.rest.dto.ToolDTO;
import com.erwan.mytasks.web.rest.mapper.ToolMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Tool.
 */
@RestController
@RequestMapping("/api")
public class ToolResource {

    private final Logger log = LoggerFactory.getLogger(ToolResource.class);
        
    @Inject
    private ToolRepository toolRepository;
    
    @Inject
    private ToolMapper toolMapper;
    
    /**
     * POST  /tools -> Create a new tool.
     */
    @RequestMapping(value = "/tools",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ToolDTO> createTool(@RequestBody ToolDTO toolDTO) throws URISyntaxException {
        log.debug("REST request to save Tool : {}", toolDTO);
        if (toolDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tool", "idexists", "A new tool cannot already have an ID")).body(null);
        }
        Tool tool = toolMapper.toolDTOToTool(toolDTO);
        tool = toolRepository.save(tool);
        ToolDTO result = toolMapper.toolToToolDTO(tool);
        return ResponseEntity.created(new URI("/api/tools/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tool", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tools -> Updates an existing tool.
     */
    @RequestMapping(value = "/tools",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ToolDTO> updateTool(@RequestBody ToolDTO toolDTO) throws URISyntaxException {
        log.debug("REST request to update Tool : {}", toolDTO);
        if (toolDTO.getId() == null) {
            return createTool(toolDTO);
        }
        Tool tool = toolMapper.toolDTOToTool(toolDTO);
        tool = toolRepository.save(tool);
        ToolDTO result = toolMapper.toolToToolDTO(tool);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tool", toolDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tools -> get all the tools.
     */
    @RequestMapping(value = "/tools",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<ToolDTO> getAllTools() {
        log.debug("REST request to get all Tools");
        return toolRepository.findAll().stream()
            .map(toolMapper::toolToToolDTO)
            .collect(Collectors.toCollection(LinkedList::new));
            }

    /**
     * GET  /tools/:id -> get the "id" tool.
     */
    @RequestMapping(value = "/tools/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ToolDTO> getTool(@PathVariable Long id) {
        log.debug("REST request to get Tool : {}", id);
        Tool tool = toolRepository.findOne(id);
        ToolDTO toolDTO = toolMapper.toolToToolDTO(tool);
        return Optional.ofNullable(toolDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tools/:id -> delete the "id" tool.
     */
    @RequestMapping(value = "/tools/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTool(@PathVariable Long id) {
        log.debug("REST request to delete Tool : {}", id);
        toolRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tool", id.toString())).build();
    }
}
