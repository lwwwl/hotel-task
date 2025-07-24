package com.example.hoteltask.dao.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 工单表实体
 */
@Data
@Entity
@Table(name = "hotel_tasks")
public class HotelTask {

    /**
     * 主键，工单id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 标题
     */
    @Column(name = "title", nullable = false, length = 250)
    private String title;

    /**
     * 描述
     */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /**
     * 房间号id
     */
    @Column(name = "room_id")
    private Long roomId;

    /**
     * 关联的客人id
     */
    @Column(name = "guest_id")
    private Long guestId;

    /**
     * 关联的部门id
     */
    @Column(name = "dept_id")
    private Long deptId;

    /**
     * 创建人客服id
     */
    @Column(name = "creator_user_id")
    private Long creatorUserId;

    /**
     * 执行人客服id
     */
    @Column(name = "executor_user_id")
    private Long executorUserId;

    /**
     * 关联chatwoot会话id
     */
    @Column(name = "conversation_id")
    private Long conversationId;

    /**
     * 关联Flowable流程实例ID
     */
    @Column(name = "process_instance_id", length = 64)
    private String processInstanceId;

    /**
     * 到期时间
     */
    @Column(name = "deadline_time")
    private Timestamp deadlineTime;

    /**
     * 开始处理时间
     */
    @Column(name = "start_process_time")
    private Timestamp startProcessTime;

    /**
     * 完成时间
     */
    @Column(name = "complete_time")
    private Timestamp completeTime;

    /**
     * 优先级 low-低 medium-中 high-高 urgent-紧急
     */
    @Column(name = "priority")
    private String priority;

    /**
     * 执行状态 pending-待处理 in_progress-进行中 review-待确认 completed-已完成
     */
    @Column(name = "task_status")
    private String taskStatus;

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