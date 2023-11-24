package com.gamechanger.rai_server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Long points;
    private Long assignee;
    private String state;
    private String priority;
    private String tag;
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @PrePersist
    public void prePersist() {createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now();}
    @PreUpdate
    public void preUpdate() {updatedAt = LocalDateTime.now();}

    public TaskEntity(TaskEntity task) {
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.points = task.getPoints();
        this.assignee = task.getAssignee();
        this.state = task.getState();
        this.priority = task.getPriority();
    }
}
