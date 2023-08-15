package com.example.personalproject.web.controller;

import com.example.personalproject.business.Service.TaskDetailsService;
import com.example.personalproject.model.TaskDetails;
import com.example.personalproject.model.TaskDetailsRequest;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/task-details")
public class TaskDetailsController {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TaskDetailsController.class);
    private final TaskDetailsService taskDetailsService;

    public TaskDetailsController(TaskDetailsService taskDetailsService) {
        this.taskDetailsService = taskDetailsService;
    }
    @PostMapping("/{taskId}/create")
    public ResponseEntity<TaskDetails> createTaskDetails(@PathVariable Long taskId, @RequestBody TaskDetailsRequest taskDetailsRequest) {
        TaskDetails taskDetails = new TaskDetails();
        taskDetails.setDescription(taskDetailsRequest.getDescription());

        TaskDetails createdTaskDetails = taskDetailsService.createTaskDetails(taskId, taskDetails);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdTaskDetails);
    }

    @GetMapping("/{taskId}/get")
    public ResponseEntity<TaskDetails> getTaskDetails(@PathVariable Long taskId) {
        Optional<TaskDetails> taskDetails = taskDetailsService.findTaskDetailsByTaskDetailsId(taskId);
        if (taskDetails.isEmpty()) {
            log.info("Task details not found for task ID: {}", taskId);
            return ResponseEntity.notFound().build();
        }
        log.info("Task details found for task ID: {}", taskId);
        return ResponseEntity.ok(taskDetails.get());
    }

    @DeleteMapping("/{taskDetailsId}/delete")
    public ResponseEntity<String> deleteTaskDetailsById(@PathVariable("taskDetailsId") @NotNull Long taskDetailsId) {
        if (!taskDetailsService.findTaskDetailsByTaskDetailsId(taskDetailsId).isPresent()) {
            log.info("Task details not found for task details ID: {}", taskDetailsId);
            return ResponseEntity.notFound().build();
        }
        taskDetailsService.deleteTaskDetailsById(taskDetailsId);
        log.info("Task details with ID: {} deleted successfully", taskDetailsId);
        return ResponseEntity.ok("Task details with ID: " + taskDetailsId + " deleted successfully");}

    @PutMapping("/{taskDetailsId}/update")
    public ResponseEntity<TaskDetails> updateTaskDetails(@PathVariable("taskDetailsId") @NotNull Long taskDetailsId, @RequestBody TaskDetailsRequest taskDetailsRequest) {
        Optional<TaskDetails> taskDetails = taskDetailsService.findTaskDetailsByTaskDetailsId(taskDetailsId);
        if (taskDetails.isEmpty()) {
            log.info("Task details not found for task details ID: {}", taskDetailsId);
            return ResponseEntity.notFound().build();
        }
        taskDetails.get().setDescription(taskDetailsRequest.getDescription());
        TaskDetails updatedTaskDetails = taskDetailsService.createTaskDetails(taskDetailsId, taskDetails.get());
        log.info("Task details with ID: {} updated successfully", taskDetailsId);
        return ResponseEntity.ok(updatedTaskDetails);
    }



}
