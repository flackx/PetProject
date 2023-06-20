package com.example.personalproject.web.controller;

import com.example.personalproject.business.Service.TaskService;
import com.example.personalproject.model.Task;
import com.example.personalproject.model.TaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TasksController {
    private final TaskService taskService;

    @Autowired
    public TasksController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@RequestBody TaskRequest taskRequest) {
        Task createdTask = taskService.createTask(taskRequest);
        return ResponseEntity.ok(createdTask);
    }
}

