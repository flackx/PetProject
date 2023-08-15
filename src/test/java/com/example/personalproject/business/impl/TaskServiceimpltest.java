package com.example.personalproject.business.impl;

import com.example.personalproject.Mapper.TaskMapper;
import com.example.personalproject.business.Repository.TaskDetailsRepository;
import com.example.personalproject.business.Repository.TaskRepository;
import com.example.personalproject.business.Service.TaskService;
import com.example.personalproject.business.Service.impl.TaskServiceImpl;
import com.example.personalproject.model.Task;
import com.example.personalproject.model.TaskDetails;
import com.example.personalproject.model.TaskRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceimpltest {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskDetailsRepository taskDetailsRepository;

    private TaskService taskService;

    private TaskMapper taskMapper;

    @BeforeEach
    void setUp() {
        taskService = new TaskServiceImpl(taskRepository, taskDetailsRepository, taskMapper);
    }
    @Test
    void testCreateTask() {
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTitle("Sample Task");
        taskRequest.setDueDate(LocalDate.parse("2023-06-21"));
        taskRequest.setStatus(Task.Status.OPEN);
        Task savedTask = new Task();
        savedTask.setId(1L);
        savedTask.setTitle(taskRequest.getTitle());
        savedTask.setDueDate(taskRequest.getDueDate());
        savedTask.setStatus(taskRequest.getStatus());
        when(taskRepository.save(Mockito.any(Task.class))).thenReturn(savedTask);
        Optional<Task> createdTask = Optional.ofNullable(taskService.createTask(taskRequest));
        verify(taskRepository, Mockito.times(1)).save(Mockito.any(Task.class));
        Assertions.assertEquals(savedTask.getId(), createdTask.get().getId());
        Assertions.assertEquals(savedTask.getTitle(), createdTask.get().getTitle());
        Assertions.assertEquals(savedTask.getDueDate(), createdTask.get().getDueDate());
        Assertions.assertEquals(savedTask.getStatus(), createdTask.get().getStatus());
    }

    @Test
    void testDeleteTaskById() {
        Long taskId = 1L;
        Task task = new Task();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        TaskDetails taskDetails = new TaskDetails();
        when(taskDetailsRepository.findByTask(task)).thenReturn(taskDetails);
        taskService.deleteTaskById(taskId);
        verify(taskRepository).findById(taskId);
        verify(taskDetailsRepository).findByTask(task);
        verify(taskDetailsRepository).delete(taskDetails);
        verify(taskRepository).deleteById(taskId);
    }

}
