package com.example.hoteltask.model.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TaskListRequest implements Serializable {
    private List<TaskStatusRequest> requireTaskStatusList;
    private Long departmentId;
    private String priority;
}