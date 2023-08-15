package com.example.personalproject.business.Service.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.personalproject.business.Repository.TaskDetailsRepository;
import com.example.personalproject.business.Repository.TaskRepository;
import com.example.personalproject.business.Service.TaskDetailsService;
import com.example.personalproject.model.Task;
import com.example.personalproject.model.TaskDetails;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TaskDetailsImpl implements TaskDetailsService {
    private static final Logger log = LoggerFactory.getLogger(TaskDetailsImpl.class);

    private final TaskDetailsRepository taskDetailsRepository;
    private final TaskRepository taskRepository;
    @Autowired
    public TaskDetailsImpl(TaskDetailsRepository taskDetailsRepository, TaskRepository taskRepository) {
        this.taskDetailsRepository = taskDetailsRepository;
        this.taskRepository = taskRepository;
    }
    @Override
    @Transactional
    public TaskDetails createTaskDetails(Long taskId, TaskDetails taskDetails) {
        boolean detailsExist = taskDetailsRepository.existsByTaskId(taskId);
        if (detailsExist) {
            log.info("Task details already exist for task ID: {}", taskId);
            throw new IllegalArgumentException("Task details already exist for task ID: " + taskId);
        }
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + taskId));
        taskDetails.setTask(task);
        TaskDetails createdTaskDetails = taskDetailsRepository.save(taskDetails);
        log.info("Task details created successfully for task ID: {}", taskId);
        return createdTaskDetails;
    }

    @Override
    public Optional<TaskDetails> findTaskDetailsByTaskDetailsId(Long taskId) {
        Optional<TaskDetails> taskDetails = taskDetailsRepository.findByTaskId(taskId);
        return taskDetails;
    }

    @Override
    public void deleteTaskDetailsById(Long taskDetailsId) {
        taskDetailsRepository.deleteById(taskDetailsId);
    }

    @Override
    public Optional<TaskDetails> updateTaskDetails(Long taskDetailsId, TaskDetails updatedTaskDetails) {
        return Optional.ofNullable(taskDetailsRepository.save(updatedTaskDetails));
    }




}
