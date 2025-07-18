package com.example.hoteltask.dao.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

/**
 * 菜单表实体
 */
@Data
@Entity
@Table(name = "menus")
public class Menu {

    /**
     * 菜单ID（主键）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 父菜单ID（顶级为0）
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 菜单名称
     */
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * 路径/URL
     */
    @Column(name = "path", length = 200)
    private String path;

    /**
     * 前端组件路径
     */
    @Column(name = "component", length = 200)
    private String component;

    /**
     * 菜单类型（0-目录，1-菜单，2-按钮）
     */
    @Column(name = "type", nullable = false)
    private Short type;

    /**
     * 图标
     */
    @Column(name = "icon", length = 50)
    private String icon;

    /**
     * 排序
     */
    @Column(name = "sort_order")
    private Integer sortOrder;

    /**
     * 是否可见
     */
    @Column(name = "visible")
    private Boolean visible;

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