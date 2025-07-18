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
 * 部门表实体
 */
@Data
@Entity
@Table(name = "hotel_departments")
public class HotelDepartment {

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