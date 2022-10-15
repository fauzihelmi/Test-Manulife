package com.manulife.task.payload.response;

import com.manulife.task.model.Task;
import lombok.Data;

import java.util.UUID;

@Data
public class TaskResponse {
    private String Id;
    private String name, description;
    private boolean status;

    public TaskResponse(Task task) {
        Id = task.getId();
        this.name = task.getName();
        this.description = task.getDescription();
        this.status = task.getStatus();
    }
}
