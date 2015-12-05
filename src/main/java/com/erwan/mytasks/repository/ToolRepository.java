package com.erwan.mytasks.repository;

import com.erwan.mytasks.domain.Tool;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Tool entity.
 */
public interface ToolRepository extends JpaRepository<Tool,Long> {

}
