package com.example.hoteltask.enums;

import lombok.Getter;

/**
 * 工单状态枚举
 */
@Getter
public enum TaskStatusEnum {
    PENDING(1, "待处理"),
    IN_PROGRESS(2, "进行中"),
    PENDING_CONFIRMATION(3, "待确认"),
    COMPLETED(4, "已完成");

    private final Integer code;
    private final String displayName;

    TaskStatusEnum(Integer code, String displayName) {
        this.code = code;
        this.displayName = displayName;
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

    /**
     * 根据名称获取枚举
     */
    public static TaskStatusEnum getByName(String name) {
        if (name == null) {
            return null;
        }
        try {
            return valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
} 