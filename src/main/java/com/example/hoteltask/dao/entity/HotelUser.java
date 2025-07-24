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
 * 客服表实体
 */
@Data
@Entity
@Table(name = "hotel_users")
public class HotelUser {

    /**
     * 用户ID（主键）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     */
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    /**
     * 加密后的密码
     */
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    /**
     * 姓名
     */
    @Column(name = "display_name", length = 100)
    private String displayName;

    /**
     * 工号
     */
    @Column(name = "employee_number", length = 50)
    private String employeeNumber;

    /**
     * 邮箱地址
     */
    @Column(name = "email", length = 100)
    private String email;

    /**
     * 手机号
     */
    @Column(name = "phone", length = 20)
    private String phone;

    /**
     * 是否为超级管理员
     */
    @Column(name = "super_admin")
    private Boolean superAdmin;

    /**
     * 额外信息，三方通知管道等
     */
    @Column(name = "extra_infos", length = 1000)
    private String extraInfos;

    /**
     * 状态（0-禁用，1-启用）
     */
    @Column(name = "active")
    private Short active;

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