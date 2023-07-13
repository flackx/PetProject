package com.example.personalproject.web.controller;

import com.example.personalproject.business.Service.TaskService;
import com.example.personalproject.model.Comment;
import com.example.personalproject.model.Task;
import com.example.personalproject.model.TaskRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @Operation(summary = "Create a new task", description = "Create a new task with the given title, due date and status", tags = {"Tasks", "Create"})
    @ApiResponses({@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = TasksController.class), mediaType = "application/json")}), @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}), @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})

    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@Parameter(description = "Create a task by adding required info") @RequestBody TaskRequest taskRequest) {
        Task createdTask = taskService.createTask(taskRequest);
        log.info("Created task with id: {}", createdTask.getId());
        return ResponseEntity.ok(createdTask);
    }

    @Operation(summary = "Delete a task", description = "Delete a task and its details by given id", tags = {"Tasks", "Delete"})
    @ApiResponses({@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = TasksController.class), mediaType = "application/json")}), @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}), @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<Task> deleteTask(@Parameter(description = "Delete a Task and its details by given id") @PathVariable("taskId") Long taskId) {
        Optional<Task> task = taskService.deleteTaskById(taskId);
        if (task.isPresent()) {
            log.info("Deleting task with id: {}", taskId);
        } else {
            log.info("Task with id: {} not found", taskId);
        }
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Update a task", description = "Update a task with the given id, title, due date and status", tags = {"Tasks", "Update"})
    @ApiResponses({@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = TasksController.class), mediaType = "application/json")}), @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}), @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PutMapping("/update/{taskId}")
    public ResponseEntity<Task> updateTask(@Parameter(description = "Update a task by adding required info") @RequestBody Task Task, @Parameter(description = "Update a task by given id") @PathVariable("taskId") Long taskId) {
        Optional<Task> updatedTask = taskService.updateTask(taskId, Task);
        if (!Task.getId().equals(taskId)) {
            log.error("Task id: {} does not match with the given id: {}", Task.getId(), taskId);
            return ResponseEntity.badRequest().build();
        }
        if (updatedTask.isPresent()) {
            log.info("Updating task with id: {}", taskId);
            return ResponseEntity.ok(updatedTask.get());
        } else {
            log.info("Task with id: {} not found", taskId);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get a task", description = "Get a task and its details by given id", tags = {"Tasks", "Get"})
    @ApiResponses({@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = TasksController.class), mediaType = "application/json")}), @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}), @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/get/{taskId}")
    public ResponseEntity<Task> getTask(@Parameter(description = "Get a task and its details by given id", required = true) @NonNull @PathVariable("taskId") Long taskId) {
        Optional<Task> task = taskService.getTaskById(taskId);
        if (task.isPresent()) {
            return ResponseEntity.ok(task.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Create a new comment", description = "Create a new comment to a task with the given id", tags = {"Tasks", "Comments", "Create"})
    @ApiResponses({@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = TasksController.class), mediaType = "application/json")}), @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}), @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/{taskId}/create/comment")
    public ResponseEntity<Comment> addCommentToTask(@PathVariable Long taskId, @RequestBody Comment comment) {
        if (comment == null) {
            log.error("Comment is null");
            return ResponseEntity.badRequest().build();
        }
        Optional<Task> task = taskService.getTaskById(taskId);
        if (!task.isPresent()) {
            log.error("Task with id: {} not found", taskId);
            return ResponseEntity.notFound().build();
        }
        Comment createdComment = taskService.addCommentToTask(taskId, comment);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @Operation(summary = "Get comments", description = "get all comments for a task", tags = {"Tasks", "Comments", "Get"})
    @ApiResponses({@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = TasksController.class), mediaType = "application/json")}), @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}), @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/{taskId}/get/comments")
    public ResponseEntity<List<Comment>> getCommentsForTask(@PathVariable Long taskId) {
        log.info("Getting comments for task with id: {}", taskId);
        if (taskId == null) {
            log.error("Task id is null");
            return ResponseEntity.badRequest().build();
        }
        if (!taskService.getTaskById(taskId).isPresent()) {
            log.error("Task with id: {} not found", taskId);
            return ResponseEntity.notFound().build();
        }
        if (taskService.getCommentsForTask(taskId).isEmpty()) {
            log.info("No comments found for task with id: {}", taskId);
            return ResponseEntity.noContent().build();
        }
        List<Comment> comments = taskService.getCommentsForTask(taskId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

}

