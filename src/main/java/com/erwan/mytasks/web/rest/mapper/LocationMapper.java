package com.erwan.mytasks.web.rest.mapper;

import com.erwan.mytasks.domain.*;
import com.erwan.mytasks.web.rest.dto.LocationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Location and its DTO LocationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LocationMapper {

    LocationDTO locationToLocationDTO(Location location);

    @Mapping(target = "tasks", ignore = true)
    Location locationDTOToLocation(LocationDTO locationDTO);
}
