-- Creating the hotel_guests table
CREATE TABLE hotel_guests
(
    id            BIGSERIAL PRIMARY KEY COMMENT '主键',
    chatwoot_id   VARCHAR(100) COMMENT 'chatwoot账号id',
    guest_name    VARCHAR(100) COMMENT '客人名字',
    room_number   VARCHAR(50) COMMENT '房号',
    phone_suffix  VARCHAR(20) COMMENT '手机号后四位',
    check_in_time TIMESTAMP COMMENT '入住时间戳',
    verify        SMALLINT  DEFAULT 0 COMMENT '是否验证',
    create_time   TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

    INDEX         idx_hotel_guests_chatwoot_id (chatwoot_id),
    INDEX         idx_hotel_guests_room_number (room_number),
    INDEX         idx_hotel_guests_verify (verify),
    INDEX         idx_hotel_guests_check_in_time (check_in_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客人表';
