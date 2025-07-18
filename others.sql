-- Creating the hotel_room table
CREATE TABLE hotel_room
(
    id          BIGSERIAL PRIMARY KEY COMMENT '主键',
    name        VARCHAR(128) COMMENT '房间名称',
    active      SMALLINT DEFAULT 1 COMMENT '是否有效 0-无效 1-有效',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    
    INDEX       idx_hotel_room_name (name),
    INDEX       idx_hotel_room_active (active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='房间表';
