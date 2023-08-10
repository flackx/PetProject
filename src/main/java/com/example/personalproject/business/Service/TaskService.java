package com.example.personalproject.business.Service;
import com.example.personalproject.model.Comment;
import com.example.personalproject.model.Task;
import com.example.personalproject.model.TaskRequest;
import com.example.personalproject.model.dto.TaskDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Task createTask(TaskRequest taskRequest);

    Optional<Task> deleteTaskById(Long taskId);

    Optional<Task> getTaskById(Long Id);

    Optional <Task> updateTask(Long taskId, Task Task);

    Comment addCommentToTask(Long taskId, Comment comment);

    List<Comment> getCommentsForTask(Long taskId);

    List<Task> createTasks(List<TaskRequest> taskRequests);


    List<TaskDTO> getAllTaskDTOs();


    //void deleteCommentById(Long commentId);

}
