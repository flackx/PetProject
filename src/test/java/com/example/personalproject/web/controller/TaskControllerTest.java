package com.example.personalproject.web.controller;

import com.example.personalproject.business.Service.TaskService;
import com.example.personalproject.model.Comment;
import com.example.personalproject.model.Task;
import com.example.personalproject.model.TaskRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.assertEquals;


@WebMvcTest(TasksController.class)
public class TaskControllerTest {

    @MockBean
    private TaskService taskService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper.registerModule(new JavaTimeModule());
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
                .andExpect(jsonPath("$.dueDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.status").value(Task.Status.OPEN.toString()));
        verify(taskService, times(1)).updateTask(eq(1L), any(Task.class));
    }

    @Test
    void testGetTaskById() throws Exception {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Original Task");
        task.setDueDate(LocalDate.from(LocalDateTime.now()));
        task.setStatus(Task.Status.OPEN);
        when(taskService.getTaskById(1L)).thenReturn(Optional.of(task));
        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/get/{taskId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Original Task"))
                .andExpect(jsonPath("$.dueDate").value(task.getDueDate().toString()))
                .andExpect(jsonPath("$.status").value(Task.Status.OPEN.toString()));
        verify(taskService, times(1)).getTaskById(1L);
    }

    @Test
    public void testAddCommentToTask() throws Exception {
        Long taskId = 1L;
        Comment comment = new Comment();
        comment.setContent("Test Comment");

        Task task = new Task();
        task.setId(taskId);

        Comment createdComment = new Comment();
        createdComment.setContent("Test Comment");

        Mockito.when(taskService.getTaskById(taskId)).thenReturn(Optional.of(task));
        Mockito.when(taskService.addCommentToTask(Mockito.anyLong(), Mockito.any())).thenReturn(createdComment);

        mockMvc.perform(MockMvcRequestBuilders.post("/tasks/{taskId}/create/comment", taskId)
                        .content(new ObjectMapper().writeValueAsString(comment))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is(comment.getContent())));

        Mockito.verify(taskService, Mockito.times(1)).getTaskById(taskId);
        Mockito.verify(taskService, Mockito.times(1)).addCommentToTask(Mockito.anyLong(), Mockito.any());
    }

    @Test
    void testGetCommentsForTask() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Task with Comments");
        task.setDueDate(LocalDate.now());
        task.setStatus(Task.Status.OPEN);

        List<Comment> comments = new ArrayList<>();
        Comment comment1 = new Comment();
        comment1.setId(1L);
        comment1.setContent("Comment 1");
        comment1.setTimestamp(LocalDateTime.now());
        comment1.setTask(task);
        Comment comment2 = new Comment();
        comment2.setId(2L);
        comment2.setContent("Comment 2");
        comment2.setTimestamp(LocalDateTime.now());
        comment2.setTask(task);
        comments.add(comment1);
        comments.add(comment2);

        TaskService taskService = Mockito.mock(TaskService.class);
        when(taskService.getTaskById(1L)).thenReturn(Optional.of(task));
        when(taskService.getCommentsForTask(1L)).thenReturn(comments);

        TasksController tasksController = new TasksController(taskService);

        ResponseEntity<List<Comment>> responseEntity = tasksController.getCommentsForTask(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(comments, responseEntity.getBody());
    }
}
