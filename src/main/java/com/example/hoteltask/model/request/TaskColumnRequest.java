package com.example.hoteltask.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class TaskColumnRequest implements Serializable {
    private String taskStatus;
    private Long lastTaskId;
    private Long lastTaskCreateTime;
}
