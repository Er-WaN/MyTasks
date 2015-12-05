package com.erwan.mytasks.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Tool.
 */
@Entity
@Table(name = "tool")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tool implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "to_buy")
    private Boolean toBuy;

    @Column(name = "price")
    private Float price;

    @Column(name = "number")
    private Integer number;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    
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
        Tool tool = (Tool) o;
        return Objects.equals(id, tool.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tool{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", toBuy='" + toBuy + "'" +
            ", price='" + price + "'" +
            ", number='" + number + "'" +
            ", comment='" + comment + "'" +
            '}';
    }
}
