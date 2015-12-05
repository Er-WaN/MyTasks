package com.erwan.mytasks.web.rest.mapper;

import com.erwan.mytasks.domain.*;
import com.erwan.mytasks.web.rest.dto.TaskDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Task and its DTO TaskDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TaskMapper {

    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "location.name", target = "locationName")
    @Mapping(source = "user.id", target = "userId")
    TaskDTO taskToTaskDTO(Task task);

    @Mapping(target = "toolss", ignore = true)
    @Mapping(source = "locationId", target = "location")
    @Mapping(target = "historiess", ignore = true)
    @Mapping(source = "userId", target = "user")
    Task taskDTOToTask(TaskDTO taskDTO);

    default Location locationFromId(Long id) {
        if (id == null) {
            return null;
        }
        Location location = new Location();
        location.setId(id);
        return location;
    }

    default User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
