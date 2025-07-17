package com.example.hoteltask.model.request;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class TaskCreateRequest implements Serializable {
    private String title;
    private String description;
    private Long roomId;
    private Long guestId;
    private Long deptId;
    private Long conversationId;
    private Timestamp deadlineTime;
    private String priority;
} 