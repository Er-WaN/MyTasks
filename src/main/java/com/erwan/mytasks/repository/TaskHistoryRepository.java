package com.erwan.mytasks.repository;

import com.erwan.mytasks.domain.TaskHistory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TaskHistory entity.
 */
public interface TaskHistoryRepository extends JpaRepository<TaskHistory,Long> {

}
