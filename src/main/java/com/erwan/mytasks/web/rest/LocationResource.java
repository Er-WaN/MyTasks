package com.erwan.mytasks.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.erwan.mytasks.domain.Location;
import com.erwan.mytasks.repository.LocationRepository;
import com.erwan.mytasks.web.rest.util.HeaderUtil;
import com.erwan.mytasks.web.rest.dto.LocationDTO;
import com.erwan.mytasks.web.rest.mapper.LocationMapper;
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
 * REST controller for managing Location.
 */
@RestController
@RequestMapping("/api")
public class LocationResource {

    private final Logger log = LoggerFactory.getLogger(LocationResource.class);
        
    @Inject
    private LocationRepository locationRepository;
    
    @Inject
    private LocationMapper locationMapper;
    
    /**
     * POST  /locations -> Create a new location.
     */
    @RequestMapping(value = "/locations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LocationDTO> createLocation(@RequestBody LocationDTO locationDTO) throws URISyntaxException {
        log.debug("REST request to save Location : {}", locationDTO);
        if (locationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("location", "idexists", "A new location cannot already have an ID")).body(null);
        }
        Location location = locationMapper.locationDTOToLocation(locationDTO);
        location = locationRepository.save(location);
        LocationDTO result = locationMapper.locationToLocationDTO(location);
        return ResponseEntity.created(new URI("/api/locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("location", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /locations -> Updates an existing location.
     */
    @RequestMapping(value = "/locations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LocationDTO> updateLocation(@RequestBody LocationDTO locationDTO) throws URISyntaxException {
        log.debug("REST request to update Location : {}", locationDTO);
        if (locationDTO.getId() == null) {
            return createLocation(locationDTO);
        }
        Location location = locationMapper.locationDTOToLocation(locationDTO);
        location = locationRepository.save(location);
        LocationDTO result = locationMapper.locationToLocationDTO(location);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("location", locationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /locations -> get all the locations.
     */
    @RequestMapping(value = "/locations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<LocationDTO> getAllLocations() {
        log.debug("REST request to get all Locations");
        return locationRepository.findAll().stream()
            .map(locationMapper::locationToLocationDTO)
            .collect(Collectors.toCollection(LinkedList::new));
            }

    /**
     * GET  /locations/:id -> get the "id" location.
     */
    @RequestMapping(value = "/locations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LocationDTO> getLocation(@PathVariable Long id) {
        log.debug("REST request to get Location : {}", id);
        Location location = locationRepository.findOne(id);
        LocationDTO locationDTO = locationMapper.locationToLocationDTO(location);
        return Optional.ofNullable(locationDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /locations/:id -> delete the "id" location.
     */
    @RequestMapping(value = "/locations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        log.debug("REST request to delete Location : {}", id);
        locationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("location", id.toString())).build();
    }
}
