package com.manulife.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manulife.task.model.Task;
import com.manulife.task.payload.request.TaskRequest;
import com.manulife.task.payload.response.TaskResponse;
import com.manulife.task.service.TaskService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import static org.mockito.BDDMockito.given;

@WebMvcTest
public class TaskTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void saveTaskTest() throws Exception{
        Task task = Task.builder()
                .name("Fauzi")
                .description("unit test")
                .status(true)
                .build();
        given(taskService.saveTask(any(TaskRequest.class))).willAnswer((invocation)-> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/task/newTask")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)));
        response.andReturn();

    }

    @Test
    public void getAllTaskTest() throws Exception{
        List<Task> listOfTask = new ArrayList<>();
        listOfTask.add(Task.builder().name("Fauzi").description("unit test").status(true).build());
        listOfTask.add(Task.builder().name("Helmi").description("unit test").status(true).build());
        List<TaskResponse> taskResponses = new ArrayList<>();
        listOfTask.forEach((data) -> {
            TaskResponse taskResponse = new TaskResponse(data);
            taskResponses.add(taskResponse);
        });
        given(taskService.getListTask()).willReturn(taskResponses);
        ResultActions response = mockMvc.perform(get("/api/task/GetTaskList"));
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfTask.size())));

    }

    @Test
    public void getDataByIdTest() throws Exception{
        String id = "765ff5e5-65f4-442a-8501-fd71f969649d";
        Task task = Task.builder()
                .name("Fauzi")
                .description("unit test")
                .status(true)
                .build();
        TaskResponse taskResponse = new TaskResponse(task);
        given(taskService.getDataById(id)).willReturn(taskResponse);
        ResultActions response = mockMvc.perform(get("/api/task/GetTask/{id}", id));
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(task.getName())))
                .andExpect(jsonPath("$.description", is(task.getDescription())))
                .andExpect(jsonPath("$.status", is(task.getStatus())));

    }

    @Test
    public void updateTaskTest() throws Exception{
        String id = "765ff5e5-65f4-442a-8501-fd71f969649d";
        Task savedTask = Task.builder()
                .name("Fauzi")
                .description("unit test")
                .status(true)
                .build();
        Task updatedTask = Task.builder()
                .name("Helmi")
                .description("unit test")
                .status(true)
                .build();
        TaskRequest request = TaskRequest.builder()
                .name("Helmi")
                .description("unit test")
                .status(true)
                .build();
        TaskResponse taskResponse = new TaskResponse(savedTask);
        given(taskService.getDataById(id)).willReturn(taskResponse);
        given(taskService.updateTask(id, request))
                .willAnswer((invocation)-> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(put("/api/task/updateTask/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedTask)));
        response.andReturn();
    }

    @Test
    public void deleteTaskTest() throws Exception{
        String id = "765ff5e5-65f4-442a-8501-fd71f969649d";
        willDoNothing().given(taskService).deleteTask(id);
        ResultActions response = mockMvc.perform(delete("/api/task/deleteTask/{id}", id));
        response.andExpect(status().isOk())
                .andDo(print());
    }

}
