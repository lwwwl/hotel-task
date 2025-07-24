package com.example.hoteltask.enums;

import lombok.Getter;

/**
 * 工单状态枚举
 */
@Getter
public enum TaskStatusEnum {
    PENDING("pending", "待处理"),
    IN_PROGRESS("in_progress", "进行中"),
    REVIEW("review", "待确认"),
    COMPLETED("completed", "已完成");

    private final String name;
    private final String displayName;

    TaskStatusEnum(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    public static TaskStatusEnum getByName(String name) {
        if (name == null) {
            return null;
        }
        for (TaskStatusEnum status : values()) {
            if (status.getName().equals(name)) {
                return status;
            }
        }
        return null;
    }



} 