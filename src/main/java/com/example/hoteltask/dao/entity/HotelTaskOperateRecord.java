package com.example.hoteltask.dao.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

/**
 * 工单操作日志表实体
 */
@Data
@Entity
@Table(name = "hotel_task_operate_record")
public class HotelTaskOperateRecord {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 工单id
     */
    @Column(name = "task_id", nullable = false)
    private Long taskId;

    /**
     * 操作人userId
     */
    @Column(name = "operator_user_id")
    private Long operatorUserId;

    /**
     * 操作类型 1-创建工单 2-领取工单 3-完成工单 4-确认完成工单 5-转移执行人
     */
    @Column(name = "operate_type", nullable = false)
    private String operateType;

    /**
     * 创建时间
     */
    @CreationTimestamp
    @Column(name = "create_time", updatable = false)
    private Timestamp createTime;

    /**
     * 更新时间
     */
    @UpdateTimestamp
    @Column(name = "update_time")
    private Timestamp updateTime;
} 