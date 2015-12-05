package com.erwan.mytasks.web.rest.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.erwan.mytasks.domain.enumeration.TaskAction;

/**
 * A DTO for the TaskHistory entity.
 */
public class TaskHistoryDTO implements Serializable {

    private Long id;

    private TaskAction action;

    private LocalDate date;

    private String comment;

    private Integer spentTime;

    private Long taskId;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaskAction getAction() {
        return action;
    }

    public void setAction(TaskAction action) {
        this.action = action;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getSpentTime() {
        return spentTime;
    }

    public void setSpentTime(Integer spentTime) {
        this.spentTime = spentTime;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TaskHistoryDTO taskHistoryDTO = (TaskHistoryDTO) o;

        if ( ! Objects.equals(id, taskHistoryDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TaskHistoryDTO{" +
            "id=" + id +
            ", action='" + action + "'" +
            ", date='" + date + "'" +
            ", comment='" + comment + "'" +
            ", spentTime='" + spentTime + "'" +
            '}';
    }
}
