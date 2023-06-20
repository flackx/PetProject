package com.example.personalproject.web.controller;

import com.example.personalproject.business.Service.TaskDetailsService;
import com.example.personalproject.model.TaskDetails;
import com.example.personalproject.model.TaskDetailsRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskDetailsController {
    private final TaskDetailsService taskDetailsService;

    public TaskDetailsController(TaskDetailsService taskDetailsService) {
        this.taskDetailsService = taskDetailsService;
    }
    @PostMapping("/{taskId}/details")
    public ResponseEntity<TaskDetails> createTaskDetails(@PathVariable Long taskId, @RequestBody TaskDetailsRequest taskDetailsRequest) {
        TaskDetails taskDetails = new TaskDetails();
        taskDetails.setDescription(taskDetailsRequest.getDescription());

        TaskDetails createdTaskDetails = taskDetailsService.createTaskDetails(taskId, taskDetails);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdTaskDetails);
    }

}
