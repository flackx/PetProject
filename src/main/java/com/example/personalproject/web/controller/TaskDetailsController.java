package com.example.personalproject.web.controller;

import com.example.personalproject.business.Service.TaskDetailsService;
import com.example.personalproject.model.TaskDetails;
import com.example.personalproject.model.TaskDetailsRequest;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskDetailsController {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TaskDetailsController.class);
    private final TaskDetailsService taskDetailsService;

    public TaskDetailsController(TaskDetailsService taskDetailsService) {
        this.taskDetailsService = taskDetailsService;
    }
    @PostMapping("/{taskId}/create/details")
    public ResponseEntity<TaskDetails> createTaskDetails(@PathVariable Long taskId, @RequestBody TaskDetailsRequest taskDetailsRequest) {
        TaskDetails taskDetails = new TaskDetails();
        taskDetails.setDescription(taskDetailsRequest.getDescription());

        TaskDetails createdTaskDetails = taskDetailsService.createTaskDetails(taskId, taskDetails);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdTaskDetails);
    }

    @GetMapping("/{taskId}/get/details")
    public ResponseEntity<TaskDetails> getTaskDetails(@PathVariable Long taskId) {
        Optional<TaskDetails> taskDetails = taskDetailsService.findTaskDetailsByTaskDetailsId(taskId);
        if (taskDetails.isEmpty()) {
            log.info("Task details not found for task ID: {}", taskId);
            return ResponseEntity.notFound().build();
        }
        log.info("Task details found for task ID: {}", taskId);
        return ResponseEntity.ok(taskDetails.get());
    }


}
