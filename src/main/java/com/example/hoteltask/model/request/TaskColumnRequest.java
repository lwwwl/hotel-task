package com.example.hoteltask.model.request;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class TaskColumnRequest implements Serializable {
    private Integer taskStatus;
    private Long lastTaskId;
    private Timestamp lastTaskCreateTime;
}
