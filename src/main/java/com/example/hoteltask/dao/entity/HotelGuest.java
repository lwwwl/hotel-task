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
 * 客人表实体
 */
@Data
@Entity
@Table(name = "hotel_guests")
public class HotelGuest {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 客人名字
     */
    @Column(name = "guest_name", length = 100)
    private String guestName;

    /**
     * 房号
     */
    @Column(name = "room_name")
    private String roomName;

    /**
     * 手机号后四位
     */
    @Column(name = "phone_suffix", length = 20)
    private String phoneSuffix;

    /**
     * 入住时间戳
     */
    @Column(name = "check_in_time")
    private Timestamp checkInTime;

    /**
     * 退房时间
     */
    @Column(name = "leave_time")
    private Timestamp leaveTime;

    /**
     * 是否验证
     */
    @Column(name = "verify")
    private Boolean verify;

    /**
     * chatwoot联系人id
     */
    @Column(name = "chatwoot_contact_id")
    private Long chatwootContactId;

    /**
     * chatwoot资源id
     */
    @Column(name = "chatwoot_source_id", length = 100)
    private String chatwootSourceId;

    /**
     * chatwoot关联的会话id
     */
    @Column(name = "chatwoot_conversation_id")
    private Long chatwootConversationId;

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