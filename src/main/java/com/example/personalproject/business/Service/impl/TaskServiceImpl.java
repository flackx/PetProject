package com.example.personalproject.business.Service.impl;

import com.example.personalproject.business.Repository.TaskRepository;
import com.example.personalproject.business.Service.TaskService;
import com.example.personalproject.model.Task;
import com.example.personalproject.model.TaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createTask(TaskRequest taskRequest) {
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDueDate(taskRequest.getDueDate());
        task.setStatus(taskRequest.getStatus());
        return taskRepository.save(task);

    }
}
