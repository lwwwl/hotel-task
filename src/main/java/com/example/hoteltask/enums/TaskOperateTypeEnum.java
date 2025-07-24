package com.example.hoteltask.enums;

import lombok.Getter;

/**
 * 工单操作类型枚举
 */
@Getter
public enum TaskOperateTypeEnum {
    CREATE(1, "创建工单"),
    CLAIM(2, "领取工单"),
    REVIEW(3, "待确认完成工单"),
    COMPLETE(4, "完成工单"),
    TRANSFER(5, "转移执行人"),
    UPDATE(6, "更新工单"),
    DELETE(7, "删除工单"),
    REMIND(8, "催办工单"),
    START_PROCESS(9, "开始处理工单");

    private final Integer code;
    private final String displayName;

    TaskOperateTypeEnum(Integer code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
    
    /**
     * 获取操作类型代码
     */
    public Integer getCode() {
        return this.code;
    }

    /**
     * 根据code获取枚举
     */
    public static TaskOperateTypeEnum getByCode(Integer code) {
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