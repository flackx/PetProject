package com.example.personalproject.business.Service;
import com.example.personalproject.model.Task;
import com.example.personalproject.model.TaskRequest;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Task createTask(TaskRequest taskRequest);
}
