package com.erwan.mytasks.web.rest.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.erwan.mytasks.domain.enumeration.TaskPriority;
import com.erwan.mytasks.domain.enumeration.TaskComplexity;
import com.erwan.mytasks.domain.enumeration.TaskState;

/**
 * A DTO for the Task entity.
 */
public class TaskDTO implements Serializable {

    private Long id;

    private String name;

    private LocalDate creationDate;

    private LocalDate lastEditDate;

    private LocalDate finishDate;

    private TaskPriority priority;

    private TaskComplexity complexity;

    private TaskState state;

    private Integer estimatedTime;

    private String description;

    private String comment;

    private Long locationId;

    private String locationName;

    private Long userId;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getLastEditDate() {
        return lastEditDate;
    }

    public void setLastEditDate(LocalDate lastEditDate) {
        this.lastEditDate = lastEditDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public TaskComplexity getComplexity() {
        return complexity;
    }

    public void setComplexity(TaskComplexity complexity) {
        this.complexity = complexity;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public Integer getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Integer estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TaskDTO taskDTO = (TaskDTO) o;

        if ( ! Objects.equals(id, taskDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TaskDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", creationDate='" + creationDate + "'" +
            ", lastEditDate='" + lastEditDate + "'" +
            ", finishDate='" + finishDate + "'" +
            ", priority='" + priority + "'" +
            ", complexity='" + complexity + "'" +
            ", state='" + state + "'" +
            ", estimatedTime='" + estimatedTime + "'" +
            ", description='" + description + "'" +
            ", comment='" + comment + "'" +
            '}';
    }
}
