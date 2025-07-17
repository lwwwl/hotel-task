package com.example.hoteltask.enums;

import lombok.Getter;

/**
 * 工单优先级枚举
 */
@Getter
public enum TaskPriority {
    LOW(1, "低"),
    MEDIUM(2, "中"),
    HIGH(3, "高"),
    URGENT(4, "紧急");

    private final Integer code;
    private final String displayName;

    TaskPriority(Integer code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    /**
     * 根据code获取枚举
     */
    public static TaskPriority getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (TaskPriority priority : values()) {
            if (priority.getCode().equals(code)) {
                return priority;
            }
        }
        return null;
    }

    /**
     * 根据名称获取枚举
     */
    public static TaskPriority getByName(String name) {
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