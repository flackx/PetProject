package com.example.personalproject.business.impl;

import com.example.personalproject.business.Repository.TaskDetailsRepository;
import com.example.personalproject.business.Repository.TaskRepository;
import com.example.personalproject.business.Service.impl.TaskDetailsImpl;
import com.example.personalproject.model.Task;
import com.example.personalproject.model.TaskDetails;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TaskDetailsImplTest {

    @Mock
    private TaskDetailsRepository taskDetailsRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskDetailsImpl taskDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTaskDetails() {
        Long taskId = 1L;
        Task task = new Task();
        TaskDetails taskDetailsRequest = new TaskDetails();
        taskDetailsRequest.setDescription("Sample Description");
        when(taskRepository.findById(taskId)).thenReturn(java.util.Optional.of(task));
        when(taskDetailsRepository.existsByTaskId(taskId)).thenReturn(false);
        when(taskDetailsRepository.save(any(TaskDetails.class))).thenReturn(taskDetailsRequest);
        TaskDetails createdTaskDetails = taskDetailsService.createTaskDetails(taskId, taskDetailsRequest);
        assertNotNull(createdTaskDetails);
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskDetailsRepository, times(1)).existsByTaskId(taskId);
        verify(taskDetailsRepository, times(1)).save(any(TaskDetails.class));
        verifyNoMoreInteractions(taskRepository, taskDetailsRepository);
    }

    @Test
    void testCreateTaskDetailsWithExistingDetails() {
        Long taskId = 1L;
        TaskDetails taskDetailsRequest = new TaskDetails();
        taskDetailsRequest.setDescription("Sample Description");
        when(taskDetailsRepository.existsByTaskId(taskId)).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () ->
                taskDetailsService.createTaskDetails(taskId, taskDetailsRequest));
        verify(taskDetailsRepository, times(1)).existsByTaskId(taskId);
        verifyNoMoreInteractions(taskRepository, taskDetailsRepository);
    }

    @Test
    void testCreateTaskDetailsWithNonExistingTask() {
        Long taskId = 1L;
        TaskDetails taskDetailsRequest = new TaskDetails();
        taskDetailsRequest.setDescription("Sample Description");
        when(taskDetailsRepository.existsByTaskId(taskId)).thenReturn(false);
        when(taskRepository.findById(taskId)).thenReturn(java.util.Optional.empty());
        assertThrows(EntityNotFoundException.class, () ->
                taskDetailsService.createTaskDetails(taskId, taskDetailsRequest));
        verify(taskDetailsRepository, times(1)).existsByTaskId(taskId);
        verify(taskRepository, times(1)).findById(taskId);
        verifyNoMoreInteractions(taskRepository, taskDetailsRepository);
    }


}

