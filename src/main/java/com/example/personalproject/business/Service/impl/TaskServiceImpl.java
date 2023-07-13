package com.example.personalproject.business.Service.impl;

import com.example.personalproject.business.Repository.TaskDetailsRepository;
import com.example.personalproject.business.Repository.TaskRepository;
import com.example.personalproject.business.Service.TaskService;
import com.example.personalproject.model.Comment;
import com.example.personalproject.model.Task;
import com.example.personalproject.model.TaskDetails;
import com.example.personalproject.model.TaskRequest;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TaskServiceImpl implements TaskService {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TaskServiceImpl.class);
    private final TaskRepository taskRepository;
    private final TaskDetailsRepository taskDetailsRepository;
    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, TaskDetailsRepository taskDetailsRepository) {
        this.taskRepository = taskRepository;
        this.taskDetailsRepository = taskDetailsRepository;
    }

    @Override
    public Task createTask(TaskRequest taskRequest) {
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDueDate(taskRequest.getDueDate());
        task.setStatus(taskRequest.getStatus());
        return taskRepository.save(task);
    }
    @Override
    public Optional<Task> deleteTaskById(Long taskId) {
        log.info("Deleting task with id: {}", taskId);
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            log.info("Task with id: {} found", taskId);
            Task task = taskOptional.get();
            log.info("Deleting task details for task with id: {}", taskId);
            TaskDetails taskDetails = taskDetailsRepository.findByTask(task);
            if (taskDetails != null) {
                taskDetailsRepository.delete(taskDetails);
            }
            log.info("Deleting task with id: {}", taskId);
            taskRepository.deleteById(taskId);
        } else {
            log.info("Task with id: {} not found", taskId);
        }
        return taskOptional;
    }

    @Override
    public Optional<Task> updateTask(Long taskId, Task updatedTask) {
        return Optional.ofNullable(taskRepository.save(updatedTask));
    }

    @Override
    public Optional<Task>getTaskById(Long Id){
        return taskRepository.findById(Id);
    }

    public Comment addCommentToTask(Long taskId, Comment comment) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + taskId));
        log.info("Adding comment to task with id: {}", taskId);
        comment.setTask(task);
        task.getComments().add(comment);
        log.info("Saving comment to task with id: {}", taskId);
        Comment savedComment = taskRepository.save(task).getComments().get(task.getComments().size() - 1);
        log.info("Comment saved to task with id: {}", taskId);
        return savedComment;
    }

    public List<Comment> getCommentsForTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + taskId));
        return task.getComments();
    }
    /*
    @Override
    public void deleteCommentById(Long commentId) {
        Task task = taskRepository.findByCommentId(commentId);
        if (task != null) {
            log.info("Deleting comment with id: {}", commentId);
            task.getComments().removeIf(comment -> comment.getId().equals(commentId));
            taskRepository.save(task);
        } else {
            log.info("Comment with id: {} not found", commentId);
        }
    }
    */

}
