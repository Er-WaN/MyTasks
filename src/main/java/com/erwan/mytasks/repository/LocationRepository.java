package com.erwan.mytasks.repository;

import com.erwan.mytasks.domain.Location;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Location entity.
 */
public interface LocationRepository extends JpaRepository<Location,Long> {

}
