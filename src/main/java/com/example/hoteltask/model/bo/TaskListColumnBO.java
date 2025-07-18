package com.example.hoteltask.model.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TaskListColumnBO implements Serializable {
    private Integer taskStatus;
    private String taskStatusDisplayName;
    private Integer taskCount;
    private List<TaskListItemBO> tasks;
}
