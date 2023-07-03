package com.example.personalproject.business.Service;


import com.example.personalproject.model.TaskDetails;
import com.example.personalproject.model.dto.TaskDetailsDTO;

import java.util.Optional;


public interface TaskDetailsService {
    TaskDetails createTaskDetails(Long taskId, TaskDetails taskDetails);

    Optional <TaskDetails> findTaskDetailsByTaskDetailsId(Long taskId);

    TaskDetailsDTO getTaskDetailsByTaskId(Long taskId);
}
