package com.example.personalproject.web.controller;

import com.example.personalproject.business.Service.TaskService;
import com.example.personalproject.model.Task;
import com.example.personalproject.model.TaskRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Tasks", description = "All tasks related endpoints")
@RestController
@RequestMapping("/tasks")
public class TasksController {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TasksController.class);
    private final TaskService taskService;

    @Autowired
    public TasksController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Create a new task",
                description = "Create a new task with the given title, due date and status",
                tags = { "Tasks","Create" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = TasksController.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })

    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@Parameter(description = "Create a task by adding required info") @RequestParam(required = false)
                                               @RequestBody TaskRequest taskRequest) {
        Task createdTask = taskService.createTask(taskRequest);
        return ResponseEntity.ok(createdTask);
    }

    @Operation(summary = "Delete a task",
                description = "Delete a task and its details by given id",
                tags = { "Tasks", "Delete" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = TasksController.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @DeleteMapping("/delete/{taskId}")
    public void deleteTask(@Parameter(description = "Delete a Task and its details by given id")@RequestParam(required = false) @PathVariable Long taskId) {
        if (taskId == null) {
            throw new IllegalArgumentException("Task id cannot be null");
        }
        if (taskId <= 0) {
            throw new IllegalArgumentException("Task id cannot be negative or zero");
        }
        log.info("Deleting task with id: {}", taskId);
        taskService.deleteTaskById(taskId);
    }
}

