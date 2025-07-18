package com.example.hoteltask.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class TaskUpdateRequest implements Serializable {
    private Long taskId;
    private String title;
    private String description;
    private Long deptId;
    private Long deadlineTime;
    private Integer priority;
} 