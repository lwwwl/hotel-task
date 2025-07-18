package com.example.hoteltask.model.request;

import java.io.Serializable;

import lombok.Data;

@Data
public class TaskChangeStatusRequest implements Serializable {
    private Long taskId;
    private Integer newTaskStatus;
} 