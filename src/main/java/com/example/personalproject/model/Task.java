package com.example.personalproject.model;

import com.example.personalproject.serializer.TaskSerializer;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tasks")
@JsonSerialize(using = TaskSerializer.class) // Apply the custom serializer
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Task(Long id, String title, LocalDate dueDate, Status status) {
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
        this.status = status;
    }
    public Task() {}

    public List<Comment> getComments() {
        return comments;
    }

    public enum Status {
        OPEN,
        IN_PROGRESS,
        COMPLETED,
        ON_HOLD,
        CANCELLED,
        OVERDUE
    }

    @JsonManagedReference
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();


}
