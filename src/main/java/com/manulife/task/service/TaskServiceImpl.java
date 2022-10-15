package com.manulife.task.service;

import com.manulife.task.dto.TaskDTO;
import com.manulife.task.exception.ResourceNotFoundException;
import com.manulife.task.model.Task;
import com.manulife.task.payload.request.TaskRequest;
import com.manulife.task.payload.response.TaskResponse;
import com.manulife.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{

    @Autowired
    private TaskRepository taskRepository;

    private Task findById(String id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Task.class.getSimpleName(), id));
    }

    @Override
    public List<TaskResponse> getListTask() {
        List<Task> task = taskRepository.findAll();
        List<TaskResponse> taskResponses = new ArrayList<>();
        task.forEach((data) -> {
            TaskResponse taskResponse = new TaskResponse(data);
            taskResponses.add(taskResponse);
        });
        return taskResponses;
    }

    @Override
    public TaskResponse getDataById(String id) {
        Task task = findById(id);
        return new TaskResponse(task);
    }

    @Override
    public Task saveTask(TaskRequest taskRequest) {
        List<TaskDTO> taskDTO = taskRepository.getDataByName(taskRequest.getName());
        Task task = new Task();
        for (TaskDTO data : taskDTO) {
            if (data == null) {
                task.setName(taskRequest.getName());
                task.setDescription(taskRequest.getDescription());
                task.setStatus(taskRequest.isStatus());
                taskRepository.save(task);
            }
        }
        return task;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Task updateTask(String id, TaskRequest taskRequest) {
        Task task = findById(id);
        if (task.getId() != null) {
            task.setName(taskRequest.getName());
            task.setDescription(taskRequest.getDescription());
            task.setStatus(taskRequest.isStatus());
            taskRepository.save(task);
        }
        return task;
    }

    @Transactional
    @Override
    public void deleteTask(String id) {
        Task task = findById(id);
        if (task.getId() != null) {
            taskRepository.delete(task);
        }
    }

}
