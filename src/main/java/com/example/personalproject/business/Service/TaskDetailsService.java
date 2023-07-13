package com.example.personalproject.business.Service;


import com.example.personalproject.model.TaskDetails;

import java.util.Optional;


public interface TaskDetailsService {
    TaskDetails createTaskDetails(Long taskId, TaskDetails taskDetails);

    Optional <TaskDetails> findTaskDetailsByTaskDetailsId(Long taskId);

    void deleteTaskDetailsById(Long taskDetailsId);

    Optional<TaskDetails> updateTaskDetails(Long taskDetailsId, TaskDetails updatedTaskDetails);
}
