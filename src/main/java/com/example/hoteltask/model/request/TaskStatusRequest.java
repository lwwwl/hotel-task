package com.example.hoteltask.model.request;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class TaskStatusRequest implements Serializable {
    private String taskStatus;
    private Long lastTaskId;
    private Timestamp lastTaskCreateTime;
}
