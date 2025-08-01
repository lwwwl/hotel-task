-- hotel_guests 表
CREATE TABLE hotel_guests
(
    id                   BIGSERIAL PRIMARY KEY,
    chatwoot_id          VARCHAR(100),
    guest_name           VARCHAR(100),
    room_number          VARCHAR(50),
    phone_suffix         VARCHAR(20),
    check_in_time        TIMESTAMP,
    verify               SMALLINT  DEFAULT 0,
    chatwoot_contact_id  BIGINT,
    chatwoot_resource_id VARCHAR(100),
    create_time          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time          TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE hotel_guests IS '客人表';
COMMENT ON COLUMN hotel_guests.id IS '主键';
COMMENT ON COLUMN hotel_guests.chatwoot_id IS 'chatwoot账号id';
COMMENT ON COLUMN hotel_guests.guest_name IS '客人名字';
COMMENT ON COLUMN hotel_guests.room_number IS '房号';
COMMENT ON COLUMN hotel_guests.phone_suffix IS '手机号后四位';
COMMENT ON COLUMN hotel_guests.check_in_time IS '入住时间戳';
COMMENT ON COLUMN hotel_guests.verify IS '是否验证';
COMMENT ON COLUMN hotel_guests.chatwoot_contact_id IS 'chatwoot联系人id';
COMMENT ON COLUMN hotel_guests.chatwoot_resource_id IS 'chatwoot资源id';
COMMENT ON COLUMN hotel_guests.create_time IS '创建时间';
COMMENT ON COLUMN hotel_guests.update_time IS '更新时间';

CREATE INDEX idx_hotel_guests_chatwoot_id ON hotel_guests (chatwoot_id);
CREATE INDEX idx_hotel_guests_room_number ON hotel_guests (room_number);
CREATE INDEX idx_hotel_guests_verify ON hotel_guests (verify);
CREATE INDEX idx_hotel_guests_check_in_time ON hotel_guests (check_in_time);
CREATE INDEX idx_hotel_guests_chatwoot_resource_id ON hotel_guests (chatwoot_resource_id);