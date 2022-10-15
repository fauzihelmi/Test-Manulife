package com.manulife.task.payload.request;

import lombok.Data;

@Data
public class TaskRequest {
    private String name, description;
    private boolean status;
}
