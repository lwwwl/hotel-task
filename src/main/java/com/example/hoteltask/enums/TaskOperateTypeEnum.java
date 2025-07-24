package com.example.hoteltask.enums;

import lombok.Getter;

/**
 * 工单操作类型枚举
 */
@Getter
public enum TaskOperateTypeEnum {
    CREATE("create", "创建工单"),
    CLAIM("claim", "领取工单"),
    REVIEW("review", "待确认完成工单"),
    COMPLETE("complete", "完成工单"),
    TRANSFER("transfer", "转移执行人"),
    UPDATE("update", "更新工单"),
    DELETE("delete", "删除工单"),
    REMIND("remind", "催办工单"),
    START_PROCESS("start_process", "开始处理工单");

    private final String code;
    private final String displayName;

    TaskOperateTypeEnum(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
    
    /**
     * 获取操作类型代码
     */
    public String getCode() {
        return this.code;
    }

    /**
     * 根据code获取枚举
     */
    public static TaskOperateTypeEnum getByCode(String code) {
        if (code == null) {
            return null;
        }
        for (TaskOperateTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

    /**
     * 根据名称获取枚举
     */
    public static TaskOperateTypeEnum getByName(String name) {
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