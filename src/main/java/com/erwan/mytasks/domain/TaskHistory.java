package com.erwan.mytasks.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.erwan.mytasks.domain.enumeration.TaskAction;

/**
 * A TaskHistory.
 */
@Entity
@Table(name = "task_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TaskHistory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "action")
    private TaskAction action;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "comment")
    private String comment;

    @Column(name = "spent_time")
    private Integer spentTime;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    
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

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TaskHistory taskHistory = (TaskHistory) o;
        return Objects.equals(id, taskHistory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TaskHistory{" +
            "id=" + id +
            ", action='" + action + "'" +
            ", date='" + date + "'" +
            ", comment='" + comment + "'" +
            ", spentTime='" + spentTime + "'" +
            '}';
    }
}
