package com.example.personalproject.business.Service.impl;

import com.example.personalproject.business.Repository.TaskDetailsRepository;
import com.example.personalproject.business.Repository.TaskRepository;
import com.example.personalproject.business.Service.TaskDetailsService;
import com.example.personalproject.model.Task;
import com.example.personalproject.model.TaskDetails;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskDetailsImpl implements TaskDetailsService {

    private final TaskDetailsRepository taskDetailsRepository;
    private final TaskRepository taskRepository;
    @Autowired
    public TaskDetailsImpl(TaskDetailsRepository taskDetailsRepository, TaskRepository taskRepository) {
        this.taskDetailsRepository = taskDetailsRepository;
        this.taskRepository = taskRepository;
    }
    @Transactional
    public TaskDetails createTaskDetails(Long taskId, TaskDetails taskDetails) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));

        taskDetails.setTask(task);
        TaskDetails createdTaskDetails = taskDetailsRepository.save(taskDetails);
        return createdTaskDetails;
    }
}
