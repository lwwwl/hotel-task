package com.example.hoteltask.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class TaskDetailRequest implements Serializable {
    private Long taskId;
} 