package com.example.personalproject.web.controller;

import com.example.personalproject.business.Service.TaskDetailsService;
import com.example.personalproject.model.TaskDetails;
import com.example.personalproject.model.TaskDetailsRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(TaskDetailsController.class)
public class TaskDetailsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskDetailsService taskDetailsService;

    @Test
    void testCreateTaskDetails() throws Exception {
        TaskDetailsRequest taskDetailsRequest = new TaskDetailsRequest();
        taskDetailsRequest.setDescription("Sample Task Details");
        TaskDetails createdTaskDetails = new TaskDetails();
        createdTaskDetails.setDescription(taskDetailsRequest.getDescription());
        Mockito.when(taskDetailsService.createTaskDetails(Mockito.anyLong(), Mockito.any(TaskDetails.class))).thenReturn(createdTaskDetails);
        mockMvc.perform(MockMvcRequestBuilders.post("/tasks/{taskId}/details", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskDetailsRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath("$.description")
                        .value(taskDetailsRequest.getDescription()));
    }
}
