package com.example.hoteltask.enums;

import lombok.Getter;

/**
 * 工单操作类型枚举
 */
@Getter
public enum TaskOperateType {
    CREATE(1, "创建工单"),
    CLAIM(2, "领取工单"),
    COMPLETE(3, "完成工单"),
    CONFIRM_COMPLETE(4, "确认完成工单"),
    TRANSFER(5, "转移执行人"),
    UPDATE(6, "更新工单"),
    DELETE(7, "删除工单"),
    REMIND(8, "催办工单");

    private final Integer code;
    private final String displayName;

    TaskOperateType(Integer code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    /**
     * 根据code获取枚举
     */
    public static TaskOperateType getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (TaskOperateType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

    /**
     * 根据名称获取枚举
     */
    public static TaskOperateType getByName(String name) {
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