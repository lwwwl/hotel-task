package com.example.hoteltask.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class TaskCreateRequest implements Serializable {
    private String title;
    private String description;
    private Long roomId;
    private Long guestId;
    private Long deptId;
    private Long conversationId;
    private Long deadlineTime;
    private String priority;
} 