package com.example.hoteltask.enums;

import lombok.Getter;

/**
 * 工单优先级枚举
 */
@Getter
public enum TaskPriorityEnum {
    LOW("low", "低"),
    MEDIUM("medium", "中"),
    HIGH("high", "高"),
    URGENT("urgent", "紧急");

    private final String code;
    private final String displayName;

    TaskPriorityEnum(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    /**
     * 根据code获取枚举
     */
    public static TaskPriorityEnum getByCode(String code) {
        if (code == null) {
            return null;
        }
        for (TaskPriorityEnum priority : values()) {
            if (priority.getCode().equals(code)) {
                return priority;
            }
        }
        return null;
    }

    /**
     * 根据名称获取枚举
     */
    public static TaskPriorityEnum getByName(String name) {
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