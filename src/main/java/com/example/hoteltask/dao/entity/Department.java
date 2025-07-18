package com.example.hoteltask.dao.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

/**
 * 部门表实体
 */
@Data
@Entity
@Table(name = "departments")
public class Department {

    /**
     * 部门ID（主键）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 部门名称
     */
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * 上级部门ID（顶级为0）
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 部门领导用户id
     */
    @Column(name = "leader_user_id")
    private Long leaderUserId;

    /**
     * 人数
     */
    @Column(name = "member_count")
    private Integer memberCount;

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