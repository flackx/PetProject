package com.example.personalproject.business.Service.impl;

import com.example.personalproject.business.Repository.TaskDetailsRepository;
import com.example.personalproject.business.Repository.TaskRepository;
import com.example.personalproject.business.Service.TaskDetailsService;
import com.example.personalproject.model.Task;
import com.example.personalproject.model.TaskDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
public class TaskDetailsImpl implements TaskDetailsService {

    private final TaskDetailsRepository taskDetailsRepository;

    @Autowired
    public TaskDetailsImpl(TaskDetailsRepository taskDetailsRepository) {
        this.taskDetailsRepository = taskDetailsRepository;
    }


    @Override
    public TaskDetails createTaskDetails(Long taskId, TaskDetails taskDetails) {
        return null;
    }
}
