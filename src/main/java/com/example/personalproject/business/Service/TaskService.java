package com.example.personalproject.business.Service;
import com.example.personalproject.model.Task;
import com.example.personalproject.model.TaskRequest;

import java.util.Optional;

public interface TaskService {

    Task createTask(TaskRequest taskRequest);

    Optional<Task> deleteTaskById(Long taskId);

    Optional<Task> getTaskById(Long Id);

    Optional <Task> updateTask(Long taskId, Task Task);

}
