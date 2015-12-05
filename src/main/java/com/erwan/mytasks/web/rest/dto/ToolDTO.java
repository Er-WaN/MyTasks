package com.erwan.mytasks.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Tool entity.
 */
public class ToolDTO implements Serializable {

    private Long id;

    private String name;

    private Boolean toBuy;

    private Float price;

    private Integer number;

    private String comment;

    private Long taskId;

    private String taskName;

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

    public Boolean getToBuy() {
        return toBuy;
    }

    public void setToBuy(Boolean toBuy) {
        this.toBuy = toBuy;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ToolDTO toolDTO = (ToolDTO) o;

        if ( ! Objects.equals(id, toolDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ToolDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", toBuy='" + toBuy + "'" +
            ", price='" + price + "'" +
            ", number='" + number + "'" +
            ", comment='" + comment + "'" +
            '}';
    }
}
