package com.example.hoteltask.model.bo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class TaskListColumnBO implements Serializable {
    private String taskStatus;
    private String taskStatusDisplayName;
    private Integer taskCount;
    private List<TaskListItemBO> tasks;
}
