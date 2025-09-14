package com.example.hoteltask.model.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TaskListItemBO implements Serializable {
    private Long taskId;
    private String title;
    private String description;
    private Long roomId;
    private String roomName;
    private Long guestId;
    private String guestName;
    private Long deptId;
    private String deptName;
    private String taskStatus;
    private String taskStatusDisplayName;
    private String priority;
    private String priorityDisplayName;
    private Long createTime;
    private Long updateTime;
    private Long deadlineTime;
    private Long completeTime;
}
