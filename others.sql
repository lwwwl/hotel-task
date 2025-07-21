-- hotel_room 表
CREATE TABLE hotel_room
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(128),
    active      SMALLINT  DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE hotel_room IS '房间表';
COMMENT ON COLUMN hotel_room.id IS '主键';
COMMENT ON COLUMN hotel_room.name IS '房间名称';
COMMENT ON COLUMN hotel_room.active IS '是否有效 0-无效 1-有效';
COMMENT ON COLUMN hotel_room.create_time IS '创建时间';
COMMENT ON COLUMN hotel_room.update_time IS '更新时间';

CREATE INDEX idx_hotel_room_name ON hotel_room (name);
CREATE INDEX idx_hotel_room_active ON hotel_room (active);
