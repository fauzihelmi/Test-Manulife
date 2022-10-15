package com.manulife.task.service;

import com.manulife.task.model.Task;
import com.manulife.task.payload.request.TaskRequest;
import com.manulife.task.payload.response.TaskResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {

    List<TaskResponse> getListTask();

    TaskResponse getDataById(String id);

    Task saveTask(TaskRequest taskRequest);

    Task updateTask(String id, TaskRequest taskRequest);

    void deleteTask(String id);
}
