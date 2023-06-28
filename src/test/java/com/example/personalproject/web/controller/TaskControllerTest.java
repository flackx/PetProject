package com.example.personalproject.web.controller;

import com.example.personalproject.business.Service.TaskService;
import com.example.personalproject.model.Task;
import com.example.personalproject.model.TaskRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.time.LocalDate;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {

    @MockBean
    private TaskService taskService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new TasksController(taskService)).build();
        when(taskService.createTask(any(TaskRequest.class))).thenAnswer(invocation -> {
            TaskRequest taskRequest = invocation.getArgument(0);
            Task task = new Task();
            task.setId(1L);
            task.setTitle(taskRequest.getTitle());
            task.setDueDate(taskRequest.getDueDate());
            task.setStatus(taskRequest.getStatus());
            return task;

        });
    }

    @Test
    void testCreateTask() throws Exception {
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTitle("Test Task");
        taskRequest.setDueDate(LocalDate.now());
        taskRequest.setStatus(Task.Status.OPEN);
        String requestBody = objectMapper.writeValueAsString(taskRequest);
        mockMvc.perform(post("/tasks/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andReturn();
    }

    @Test
    void testDeleteTask() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/tasks/delete/{id}", 1l))
                .andExpect(status().isOk());
    }
    @Test
    void testUpdateTask() throws Exception {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Original Task");
        task.setDueDate(LocalDate.now());
        task.setStatus(Task.Status.OPEN);
        String requestBody = objectMapper.writeValueAsString(task);
        when(taskService.updateTask(eq(1L), any(Task.class))).thenReturn(Optional.of(task));
        mockMvc.perform(put("/tasks/update/{taskId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Original Task"))
                .andExpect(jsonPath("$.dueDate[0]").value(LocalDate.now().getYear()))
                .andExpect(jsonPath("$.dueDate[1]").value(LocalDate.now().getMonthValue()))
                .andExpect(jsonPath("$.dueDate[2]").value(LocalDate.now().getDayOfMonth()))
                .andExpect(jsonPath("$.status").value(Task.Status.OPEN.toString()));
        verify(taskService, times(1)).updateTask(eq(1L), any(Task.class));
    }
}
