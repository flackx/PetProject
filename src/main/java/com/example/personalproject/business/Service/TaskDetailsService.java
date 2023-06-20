package com.example.personalproject.business.Service;

import com.example.personalproject.business.Repository.TaskRepository;
import com.example.personalproject.model.Task;
import com.example.personalproject.model.TaskDetails;
import com.example.personalproject.model.TaskDetailsRequest;
import com.example.personalproject.model.TaskRequest;

public interface TaskDetailsService {
    TaskDetails createTaskDetails(Long taskId, TaskDetails taskDetails);

}
