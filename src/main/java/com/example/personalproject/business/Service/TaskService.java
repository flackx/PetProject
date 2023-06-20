package com.example.personalproject.business.Service;
import com.example.personalproject.model.Task;
import com.example.personalproject.model.TaskRequest;

public interface TaskService {

    Task createTask(TaskRequest taskRequest);
}
