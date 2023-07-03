package com.example.personalproject.business.Service.impl;
import com.example.personalproject.model.dto.TaskDetailsDTO;
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


    public Optional<TaskDetails> findTaskDetailsByTaskDetailsId(Long taskId) {
        Optional<TaskDetails> taskDetails = Optional.ofNullable(taskDetailsRepository.findByTaskId(taskId));
        return taskDetails;
    }

    @Override
    public TaskDetailsDTO getTaskDetailsByTaskId(Long taskId) {
        TaskDetails taskDetails = taskDetailsRepository.findByTaskId(taskId);
        if (taskDetails == null) {
            // Handle case when no task details found for the given taskId
            // You can throw an exception or return a default/empty DTO as per your requirement
        }

        // Map the TaskDetails entity to TaskDetailsDTO
        TaskDetailsDTO taskDetailsDTO = mapToDTO(taskDetails); // Use a mapping method or library to convert entity to DTO

        return taskDetailsDTO;
    }
    private TaskDetailsDTO mapToDTO(TaskDetails taskDetails) {
        TaskDetailsDTO taskDetailsDTO = new TaskDetailsDTO();
        taskDetailsDTO.setId(taskDetails.getId());
     //   taskDetailsDTO.setTaskDetails(taskDetails.getTask()); // Corrected method name
        taskDetailsDTO.setDescription(taskDetails.getDescription());
        // Set other properties as needed
        return taskDetailsDTO;
    }
}
