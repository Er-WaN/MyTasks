package com.erwan.mytasks.web.rest.mapper;

import com.erwan.mytasks.domain.*;
import com.erwan.mytasks.web.rest.dto.TaskHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TaskHistory and its DTO TaskHistoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TaskHistoryMapper {

    @Mapping(source = "task.id", target = "taskId")
    TaskHistoryDTO taskHistoryToTaskHistoryDTO(TaskHistory taskHistory);

    @Mapping(source = "taskId", target = "task")
    TaskHistory taskHistoryDTOToTaskHistory(TaskHistoryDTO taskHistoryDTO);

    default Task taskFromId(Long id) {
        if (id == null) {
            return null;
        }
        Task task = new Task();
        task.setId(id);
        return task;
    }
}
