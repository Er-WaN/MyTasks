package com.erwan.mytasks.web.rest.mapper;

import com.erwan.mytasks.domain.*;
import com.erwan.mytasks.web.rest.dto.ToolDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Tool and its DTO ToolDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ToolMapper {

    @Mapping(source = "task.id", target = "taskId")
    @Mapping(source = "task.name", target = "taskName")
    ToolDTO toolToToolDTO(Tool tool);

    @Mapping(source = "taskId", target = "task")
    Tool toolDTOToTool(ToolDTO toolDTO);

    default Task taskFromId(Long id) {
        if (id == null) {
            return null;
        }
        Task task = new Task();
        task.setId(id);
        return task;
    }
}
