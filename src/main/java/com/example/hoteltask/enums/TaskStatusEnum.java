package com.example.hoteltask.enums;

import lombok.Getter;

/**
 * 工单状态枚举
 */
@Getter
public enum TaskStatusEnum {
    PENDING("pending", 1, "待处理"),
    IN_PROGRESS("in_progress", 2, "进行中"),
    REVIEW("review", 3, "待确认"),
    COMPLETED("completed", 4, "已完成");

    private final String name;
    private final Integer code;
    private final String displayName;

    TaskStatusEnum(String name, Integer code, String displayName) {
        this.name = name;
        this.code = code;
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

    /**
     * 根据code获取枚举
     */
    public static TaskStatusEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (TaskStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

} 