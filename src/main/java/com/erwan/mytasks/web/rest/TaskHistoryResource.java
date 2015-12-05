package com.erwan.mytasks.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.erwan.mytasks.domain.TaskHistory;
import com.erwan.mytasks.repository.TaskHistoryRepository;
import com.erwan.mytasks.web.rest.util.HeaderUtil;
import com.erwan.mytasks.web.rest.dto.TaskHistoryDTO;
import com.erwan.mytasks.web.rest.mapper.TaskHistoryMapper;
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
 * REST controller for managing TaskHistory.
 */
@RestController
@RequestMapping("/api")
public class TaskHistoryResource {

    private final Logger log = LoggerFactory.getLogger(TaskHistoryResource.class);
        
    @Inject
    private TaskHistoryRepository taskHistoryRepository;
    
    @Inject
    private TaskHistoryMapper taskHistoryMapper;
    
    /**
     * POST  /taskHistorys -> Create a new taskHistory.
     */
    @RequestMapping(value = "/taskHistorys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TaskHistoryDTO> createTaskHistory(@RequestBody TaskHistoryDTO taskHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save TaskHistory : {}", taskHistoryDTO);
        if (taskHistoryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("taskHistory", "idexists", "A new taskHistory cannot already have an ID")).body(null);
        }
        TaskHistory taskHistory = taskHistoryMapper.taskHistoryDTOToTaskHistory(taskHistoryDTO);
        taskHistory = taskHistoryRepository.save(taskHistory);
        TaskHistoryDTO result = taskHistoryMapper.taskHistoryToTaskHistoryDTO(taskHistory);
        return ResponseEntity.created(new URI("/api/taskHistorys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("taskHistory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /taskHistorys -> Updates an existing taskHistory.
     */
    @RequestMapping(value = "/taskHistorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TaskHistoryDTO> updateTaskHistory(@RequestBody TaskHistoryDTO taskHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update TaskHistory : {}", taskHistoryDTO);
        if (taskHistoryDTO.getId() == null) {
            return createTaskHistory(taskHistoryDTO);
        }
        TaskHistory taskHistory = taskHistoryMapper.taskHistoryDTOToTaskHistory(taskHistoryDTO);
        taskHistory = taskHistoryRepository.save(taskHistory);
        TaskHistoryDTO result = taskHistoryMapper.taskHistoryToTaskHistoryDTO(taskHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("taskHistory", taskHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /taskHistorys -> get all the taskHistorys.
     */
    @RequestMapping(value = "/taskHistorys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<TaskHistoryDTO> getAllTaskHistorys() {
        log.debug("REST request to get all TaskHistorys");
        return taskHistoryRepository.findAll().stream()
            .map(taskHistoryMapper::taskHistoryToTaskHistoryDTO)
            .collect(Collectors.toCollection(LinkedList::new));
            }

    /**
     * GET  /taskHistorys/:id -> get the "id" taskHistory.
     */
    @RequestMapping(value = "/taskHistorys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TaskHistoryDTO> getTaskHistory(@PathVariable Long id) {
        log.debug("REST request to get TaskHistory : {}", id);
        TaskHistory taskHistory = taskHistoryRepository.findOne(id);
        TaskHistoryDTO taskHistoryDTO = taskHistoryMapper.taskHistoryToTaskHistoryDTO(taskHistory);
        return Optional.ofNullable(taskHistoryDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /taskHistorys/:id -> delete the "id" taskHistory.
     */
    @RequestMapping(value = "/taskHistorys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTaskHistory(@PathVariable Long id) {
        log.debug("REST request to delete TaskHistory : {}", id);
        taskHistoryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("taskHistory", id.toString())).build();
    }
}
