package com.example.hoteltask.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class TaskTransferExecutorRequest implements Serializable {
    private Long taskId;
    private Long newExecutorUserId;
} 