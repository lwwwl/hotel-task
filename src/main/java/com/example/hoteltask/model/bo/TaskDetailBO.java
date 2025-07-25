package com.example.hoteltask.model.bo;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

/**
 * 工单详情业务对象
 */
@Data
public class TaskDetailBO implements Serializable {
    private Long taskId;
    private String title;
    
    private String description;
    
    private Long roomId;
    private String roomName;
    
    private Long guestId;
    private String guestName;
    
    private Long deptId;
    private String deptName;
    
    private Long creatorUserId;
    private String creatorName;
    
    private Long executorUserId;
    private String executorName;
    
    private Long conversationId;
    private String conversationName;
    
    private Long deadlineTime;
    private Long completeTime;
    
    private String priority;
    private String priorityDisplayName;
    
    private String taskStatus;
    private String taskStatusDisplayName;
    
    private Long createTime;
    private Long updateTime;
} 