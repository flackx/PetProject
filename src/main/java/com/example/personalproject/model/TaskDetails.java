package com.example.personalproject.model;


import jakarta.persistence.*;

@Entity
@Table(name = "task_details", uniqueConstraints = {
        @UniqueConstraint(columnNames = "task_id")
})
public class TaskDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    @Column(nullable = false)
    private String description;

    public TaskDetails() {
    }

    public TaskDetails(Long id, Task task, String description) {
        this.id = id;
        this.task = task;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Task getTaskDetails() {
        return task;
    }
}