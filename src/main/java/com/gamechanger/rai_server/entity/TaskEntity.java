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
public final class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long points;

    @Column(nullable = false)
    private Long assignee;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String priority;

    @Column(nullable = false)
    private String tag;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
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
        this.tag = task.getTag();
    }
}
