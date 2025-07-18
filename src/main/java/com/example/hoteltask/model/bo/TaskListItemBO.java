package com.example.hoteltask.model.bo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

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
    private Integer taskStatus;
    private String taskStatusDisplayName;
    private Timestamp createTime;
    private Timestamp updateTime;
}
