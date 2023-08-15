package com.example.personalproject.model.dto;
import java.time.LocalDate;

public class TaskDTO {
    private Long id;
    private String title;
    private LocalDate dueDate;
    private String status;

    public TaskDTO() {
    }

    public TaskDTO(Long id, String title, LocalDate dueDate, String status) {
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
        this.status = status;
    }

    // Getters and setters
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
